package ru.looktv.launcher.ui.models.common

import android.graphics.drawable.Drawable
import ru.looktv.launcher.data.models.ApplicationInfo

object UpdateStatus {
    const val NO_UPDATES = "no_updates"
    const val NEW_VERSION_IS_READY = "new_version"
    const val DOWNLOADING_NOW = "downloading"
    const val READY_TO_INSTALL = "ready_to_install"
}

data class AppItemModel(
    val name: String,
    val packageName: String?,
    val label: String,
    val icon: Drawable?,
    val updateStatus: String?
)

fun ApplicationInfo.mapToAppItem(updateStatus: String? = null) = AppItemModel(
    this.name ?: "",
    this.packageName,
    this.label ?: "",
    this.icon,
    updateStatus
)
