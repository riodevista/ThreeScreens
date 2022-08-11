package ru.looktv.launcher.data.models

import android.graphics.drawable.Drawable

class ApplicationInfo(
    var name: String? = null,
    var packageName: String? = null,
    var label: String? = null,
    var icon: Drawable? = null,
    var version: Long = 0
)