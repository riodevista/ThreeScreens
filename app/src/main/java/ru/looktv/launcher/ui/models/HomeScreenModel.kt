package ru.looktv.launcher.ui.models

import ru.looktv.launcher.ui.models.common.AppItem


data class HomeScreenModel(
    val time: String,
    val promos: List<PromoItem>,
    val apps: List<AppItem>,
    val continues: List<ContinueWatchingItem>,
    val favorites: List<FavoriteItem>,
    val showPromoProgress: Boolean
)

data class PromoItem(
    val title: String,
    val subtitle: String,
    val image: String
)

data class ContinueWatchingItem(
    val image: Int
)

data class FavoriteItem(
    val image: Int
)