package ru.looktv.launcher.domain

import android.content.Intent
import android.content.pm.PackageManager


object LauncherRepository {

    fun loadAppsList(packageManager: PackageManager): List<ApplicationInfo> {
        val appsList = emptyList<ApplicationInfo>().toMutableList()
        try {
            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            packageManager.queryIntentActivities(intent, 0).forEach {
                val activityInfo = it.activityInfo
                appsList.add(
                    ApplicationInfo(
                        name = activityInfo.name,
                        packageName = activityInfo.packageName,
                        label = activityInfo.loadLabel(packageManager).toString(),
                        icon = activityInfo.loadIcon(packageManager)
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return appsList
    }
}