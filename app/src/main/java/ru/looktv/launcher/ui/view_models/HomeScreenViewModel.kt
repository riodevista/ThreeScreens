package ru.looktv.launcher.ui.view_models

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.looktv.launcher.R
import ru.looktv.launcher.domain.LauncherRepository
import ru.looktv.launcher.ui.models.ContinueWatchingItem
import ru.looktv.launcher.ui.models.FavoriteItem
import ru.looktv.launcher.ui.models.HomeScreenModel
import ru.looktv.launcher.ui.models.PromoItem
import ru.looktv.launcher.ui.models.common.AppItem
import ru.looktv.launcher.ui.models.common.mapToAppItemsList
import ru.looktv.launcher.utils.LauncherUtils

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
            apps = emptyList(),
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

    fun loadAppsList(packageManager: PackageManager) {
        val appsList = LauncherRepository.loadAppsList(packageManager).mapToAppItemsList()
        _screenModel.update { it.copy(apps = appsList) }
    }

    fun launchApp(context: Context, appItem: AppItem) {
        LauncherUtils.startApp(context, appItem.packageName)
    }
}
