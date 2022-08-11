package ru.looktv.launcher.domain

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.looktv.launcher.data.InstalledAppsRepository
import ru.looktv.launcher.data.RemoteAppsStorageRepository
import ru.looktv.launcher.data.models.AppUpdate
import ru.looktv.launcher.ui.models.common.AppItemModel
import ru.looktv.launcher.ui.models.common.UpdateStatus
import ru.looktv.launcher.ui.models.common.mapToAppItem
import java.io.File
import java.io.FileOutputStream
import java.net.URL


object AppsInteractor {

    private val remoteAppsStorageRepository = RemoteAppsStorageRepository()
    private val installedAppsRepository = InstalledAppsRepository()

    private var appsUpdateList: List<AppUpdate> = emptyList()

    suspend fun getUpdatesList(): List<AppUpdate> {
        val appsUpdateListFromBackend = remoteAppsStorageRepository.getAppsUpdateList()
        appsUpdateList = appsUpdateListFromBackend
        return remoteAppsStorageRepository.getAppsUpdateList()
    }


    fun launchAppInstalling(appContext: Context, appName: String) {
        val appUpdate = appsUpdateList.find { it.name == appName }
        val apksDir = File(appContext.filesDir, "apks")
        val file = File(apksDir.absolutePath, appUpdate?.url?.substringAfterLast('/') ?: "")
        try {
            if (file.exists()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val downloadedApk: Uri = getFileUri(appContext, file)
                    val intent = Intent(Intent.ACTION_VIEW).setDataAndType(
                        downloadedApk,
                        "application/vnd.android.package-archive"
                    )
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    appContext.startActivity(intent)
                } else {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(
                        Uri.fromFile(file),
                        "application/vnd.android.package-archive"
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    appContext.startActivity(intent)
                }

            }
        } catch (e: Exception) {
            Toast.makeText(appContext, e.toString(), Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun getFileUri(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName
                .toString() + ".provider",
            file
        )
    }

    fun launchAppDownloading(appContext: Context, appName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val appUpdate = appsUpdateList.find { it.name == appName }
            appUpdate?.let {
                URL(it.url).openStream().use { input ->
                    FileOutputStream(
                        File(
                            appContext.filesDir,
                            appUpdate.url.substringAfterLast('/')
                        )
                    ).use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getAppsListForView(packageManager: PackageManager): List<AppItemModel> {
        val installedAppsList =
            installedAppsRepository.loadAppsList(packageManager).map { installedApp ->
                val updateStatus =
                    if ((appsUpdateList.find { it.name == installedApp.name }?.version
                            ?: 0) > installedApp.version
                    ) UpdateStatus.NEW_VERSION_IS_READY else UpdateStatus.NO_UPDATES
                installedApp.mapToAppItem(updateStatus)
            }
        return installedAppsList
    }

}