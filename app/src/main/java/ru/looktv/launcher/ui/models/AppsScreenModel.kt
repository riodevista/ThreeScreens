package ru.looktv.launcher.ui.models

import ru.looktv.launcher.ui.models.common.AppItem


data class AppsScreenModel(
    val tabs: List<String>,
    val apps: List<AppItem>,
)
