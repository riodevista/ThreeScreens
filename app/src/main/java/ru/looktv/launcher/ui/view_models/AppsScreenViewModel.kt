package ru.looktv.launcher.ui.view_models

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.looktv.launcher.domain.AppsInteractor
import ru.looktv.launcher.ui.models.AppsScreenModel
import ru.looktv.launcher.ui.models.common.AppItemModel
import kotlin.random.Random

class AppsScreenViewModel() : ViewModel() {
    private val appsInteractor = AppsInteractor
    private var appsList = emptyList<AppItemModel>()
    private val _screenModel = MutableStateFlow(
        AppsScreenModel(
            tabs = listOf(
                "Все приложения", "Топ-10", "Категории", "Установленные"
            ),
            apps = emptyList()
        )
    )
    val screenModel: StateFlow<AppsScreenModel> get() = _screenModel

    fun loadAppsList(packageManager: PackageManager) {
        appsList = appsInteractor.getAppsListForView(packageManager)
        _screenModel.update { it.copy(apps = appsList) }
    }

    fun filter(tabPosition: Int) {
        when (tabPosition) {
            0 -> _screenModel.tryEmit(
                AppsScreenModel(
                    tabs = listOf(
                        "Все приложения", "Топ-10", "Категории", "Установленные"
                    ),
                    apps = appsList
                )
            )
            1 -> {
                _screenModel.update {
                    it.copy(
                        apps = if (appsList.size > 10) appsList.subList(
                            0,
                            10
                        ) else appsList
                    )
                }
            }
            2 -> {
                _screenModel.update { it.copy(apps = appsList.shuffled()) }
            }
            else -> {
                _screenModel.update {
                    _screenModel.value.copy(
                        apps = appsList.shuffled()
                            .subList(0, Random.Default.nextInt(appsList.size - 1))
                    )
                }
            }
        }
    }
}
