package ru.looktv.launcher.domain

import android.graphics.drawable.Drawable

class ApplicationInfo(
    var name: String? = null,
    var packageName: String? = null,
    var label: String? = null,
    var icon: Drawable? = null
)