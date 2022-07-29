package ru.looktv.launcher.ui.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.looktv.launcher.R
import ru.looktv.launcher.ui.models.AppItem
import ru.looktv.launcher.ui.models.ContinueWatchingItem
import ru.looktv.launcher.ui.models.FavoriteItem
import ru.looktv.launcher.ui.models.HomeScreenModel
import ru.looktv.launcher.ui.models.PromoItem

class HomeScreenViewModel() : ViewModel() {
    private val _screenModel = MutableStateFlow(
        HomeScreenModel(
            time = "12:00",
            promos = listOf(
                PromoItem("The boys", "Сезон 1", R.drawable.dummy_film_banner_big),
                PromoItem("The boys", "Сезон 2", R.drawable.dummy_film_banner_big),
                PromoItem("The boys 2", "Сезон 1", R.drawable.dummy_film_banner_big),
                PromoItem("The boys 2", "Сезон 2", R.drawable.dummy_film_banner_big),
                PromoItem("The boys 2", "Сезон 3", R.drawable.dummy_film_banner_big)
            ),
            apps = listOf(
                AppItem(R.drawable.dummy_app),
                AppItem(R.drawable.dummy_app),
                AppItem(R.drawable.dummy_app),
                AppItem(R.drawable.dummy_app),
                AppItem(R.drawable.dummy_app),
                AppItem(R.drawable.dummy_app)
            ),
            continues = listOf(
                ContinueWatchingItem(R.drawable.dummy_continue),
                ContinueWatchingItem(R.drawable.dummy_continue),
                ContinueWatchingItem(R.drawable.dummy_continue),
                ContinueWatchingItem(R.drawable.dummy_continue),
                ContinueWatchingItem(R.drawable.dummy_continue),
                ContinueWatchingItem(R.drawable.dummy_continue)
            ),
            favorites = listOf(
                FavoriteItem(R.drawable.dummy_fav),
                FavoriteItem(R.drawable.dummy_fav),
                FavoriteItem(R.drawable.dummy_fav),
                FavoriteItem(R.drawable.dummy_fav),
                FavoriteItem(R.drawable.dummy_fav)
            )
        )
    )
    val screenModel: StateFlow<HomeScreenModel> get() = _screenModel

    init {

    }
}
