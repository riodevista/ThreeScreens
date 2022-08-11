package ru.looktv.launcher.ui.models

import ru.looktv.launcher.ui.models.common.AppItemModel


data class AppsScreenModel(
    val tabs: List<String>,
    val apps: List<AppItemModel>,
)
