package ru.looktv.launcher.ui.view_models

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.looktv.launcher.R
import ru.looktv.launcher.data.MoviesRepository
import ru.looktv.launcher.domain.AppsInteractor
import ru.looktv.launcher.ui.models.ContinueWatchingItem
import ru.looktv.launcher.ui.models.FavoriteItem
import ru.looktv.launcher.ui.models.HomeScreenModel
import ru.looktv.launcher.ui.models.PromoItem

class HomeScreenViewModel() : ViewModel() {
    private val moviesRepository = MoviesRepository()
    private val appsInteractor = AppsInteractor

    private val _screenModel = MutableStateFlow(
        HomeScreenModel(
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
        val appsList = appsInteractor.getAppsListForView(packageManager)
        _screenModel.update { it.copy(apps = appsList) }
    }
}
