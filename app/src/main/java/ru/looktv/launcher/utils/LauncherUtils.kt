package ru.looktv.launcher.utils

import android.content.Context


object LauncherUtils {

    fun startApp(context: Context, packageName: String?) {
        try {
            packageName?.let {
                context.startActivity(context.packageManager.getLaunchIntentForPackage(it))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}