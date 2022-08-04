package ru.looktv.launcher.ui.view_models

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.looktv.launcher.data.LauncherRepository
import ru.looktv.launcher.ui.models.AppsScreenModel
import ru.looktv.launcher.ui.models.common.AppItem
import ru.looktv.launcher.ui.models.common.mapToAppItemsList
import ru.looktv.launcher.utils.LauncherUtils
import kotlin.random.Random

class AppsScreenViewModel() : ViewModel() {
    private var appsList = emptyList<AppItem>()
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
        appsList = LauncherRepository.loadAppsList(packageManager).mapToAppItemsList()
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

    fun launchApp(context: Context, appItem: AppItem) {
        LauncherUtils.startApp(context, appItem.packageName)
    }
}
