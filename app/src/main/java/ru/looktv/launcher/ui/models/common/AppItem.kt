package ru.looktv.launcher.ui.models.common

import android.graphics.drawable.Drawable
import ru.looktv.launcher.data.models.ApplicationInfo

data class AppItem(
    val name: String,
    val packageName: String?,
    val label: String,
    val icon: Drawable?,
)

fun ApplicationInfo.mapToAppItem() = AppItem(
    this.name ?: "",
    this.packageName,
    this.label ?: "",
    this.icon
)

fun List<ApplicationInfo>.mapToAppItemsList() = this.map {
    it.mapToAppItem()
}