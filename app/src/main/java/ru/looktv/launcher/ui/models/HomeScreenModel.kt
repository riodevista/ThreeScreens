package ru.looktv.launcher.ui.models


data class HomeScreenModel(
    val time: String,
    val promos: List<PromoItem>,
    val apps: List<AppItem>,
    val continues: List<ContinueWatchingItem>,
    val favorites: List<FavoriteItem>
)

data class PromoItem(
    val title: String,
    val subtitle: String,
    val image: Int
)

data class AppItem(
    val image: Int
)

data class ContinueWatchingItem(
    val image: Int
)

data class FavoriteItem(
    val image: Int
)