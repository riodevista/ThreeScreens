package ru.looktv.launcher.ui.view_models

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.looktv.launcher.R
import ru.looktv.launcher.data.LauncherRepository
import ru.looktv.launcher.data.MoviesRepository
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
            promos = emptyList(),
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
            ),
            showPromoProgress = false
        )
    )

    private val moviesRepository = MoviesRepository()

    init {
        _screenModel.update { it.copy(showPromoProgress = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                moviesRepository.getMoviesList().let { movies ->
                    _screenModel.update { screenModel ->
                        screenModel.copy(promos = movies.map {
                            PromoItem(
                                it.alias,
                                "",
                                it.banner
                            )
                        }, showPromoProgress = false)
                    }
                }
            } catch (e: Exception) {
                _screenModel.update { it.copy(showPromoProgress = false) }
                // TODO Handle error
            }
        }
    }

    val screenModel: StateFlow<HomeScreenModel> get() = _screenModel

    fun loadAppsList(packageManager: PackageManager) {
        val appsList = LauncherRepository.loadAppsList(packageManager).mapToAppItemsList()
        _screenModel.update { it.copy(apps = appsList) }
    }

    fun launchApp(context: Context, appItem: AppItem) {
        LauncherUtils.startApp(context, appItem.packageName)
    }
}
