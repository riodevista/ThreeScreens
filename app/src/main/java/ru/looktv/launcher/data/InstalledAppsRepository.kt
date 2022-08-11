package ru.looktv.launcher.data

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import ru.looktv.launcher.data.models.ApplicationInfo


class InstalledAppsRepository {

    @RequiresApi(Build.VERSION_CODES.P)
    fun loadAppsList(packageManager: PackageManager): List<ApplicationInfo> {
        val appsList = emptyList<ApplicationInfo>().toMutableList()
        try {
            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            packageManager.queryIntentActivities(intent, 0).forEach {
                val activityInfo = it.activityInfo
                val packageInfo = packageManager.getPackageInfo(activityInfo.packageName, 0)
                appsList.add(
                    ApplicationInfo(
                        name = activityInfo.name,
                        packageName = activityInfo.packageName,
                        label = activityInfo.loadLabel(packageManager).toString(),
                        icon = activityInfo.loadIcon(packageManager),
                        version = packageInfo.longVersionCode,
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return appsList
    }
}