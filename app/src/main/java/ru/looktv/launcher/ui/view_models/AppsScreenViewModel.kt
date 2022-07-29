package ru.looktv.launcher.ui.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.looktv.launcher.R
import ru.looktv.launcher.ui.models.AppsScreenModel
import ru.looktv.launcher.ui.models.common.AppItem
import kotlin.random.Random

class AppsScreenViewModel() : ViewModel() {

    private val testData = listOf(
        AppItem(R.drawable.dummy_app),
        AppItem(R.drawable.dummy_app_1),
        AppItem(R.drawable.dummy_app_2),
        AppItem(R.drawable.dummy_app_3),
        AppItem(R.drawable.dummy_app_4),
        AppItem(R.drawable.dummy_app_5),
        AppItem(R.drawable.dummy_app),
        AppItem(R.drawable.dummy_app_1),
        AppItem(R.drawable.dummy_app_2),
        AppItem(R.drawable.dummy_app_3),
        AppItem(R.drawable.dummy_app_4),
        AppItem(R.drawable.dummy_app_5),
        AppItem(R.drawable.dummy_app),
        AppItem(R.drawable.dummy_app_1),
        AppItem(R.drawable.dummy_app_2),
        AppItem(R.drawable.dummy_app_3),
        AppItem(R.drawable.dummy_app_4),
        AppItem(R.drawable.dummy_app_5),
        AppItem(R.drawable.dummy_app),
        AppItem(R.drawable.dummy_app_1),
        AppItem(R.drawable.dummy_app_2),
        AppItem(R.drawable.dummy_app_3),
        AppItem(R.drawable.dummy_app_4),
        AppItem(R.drawable.dummy_app_5),
        AppItem(R.drawable.dummy_app),
        AppItem(R.drawable.dummy_app_1),
        AppItem(R.drawable.dummy_app_2),
        AppItem(R.drawable.dummy_app_3),
        AppItem(R.drawable.dummy_app_4),
        AppItem(R.drawable.dummy_app_5),
        AppItem(R.drawable.dummy_app),
        AppItem(R.drawable.dummy_app_1),
        AppItem(R.drawable.dummy_app_2),
        AppItem(R.drawable.dummy_app_3),
        AppItem(R.drawable.dummy_app_4),
        AppItem(R.drawable.dummy_app_5),
        AppItem(R.drawable.dummy_app),
        AppItem(R.drawable.dummy_app_1),
        AppItem(R.drawable.dummy_app_2),
        AppItem(R.drawable.dummy_app_3),
        AppItem(R.drawable.dummy_app_4),
        AppItem(R.drawable.dummy_app_5),
    )

    private val _screenModel = MutableStateFlow(
        AppsScreenModel(
            tabs = listOf(
                "Все приложения", "Топ-10", "Категории", "Установленные"
            ),
            apps = testData
        )
    )
    val screenModel: StateFlow<AppsScreenModel> get() = _screenModel

    fun filter(tabPosition: Int) {
        when (tabPosition) {
            0 -> _screenModel.tryEmit(
                AppsScreenModel(
                    tabs = listOf(
                        "Все приложения", "Топ-100", "Категории", "Установленные"
                    ),
                    apps = testData
                )
            )
            1 -> {
                _screenModel.update { _screenModel.value.copy(apps = testData.subList(0, 10)) }
            }
            2 -> {
                _screenModel.update { _screenModel.value.copy(apps = testData.shuffled()) }
            }
            else -> {
                _screenModel.update {
                    _screenModel.value.copy(
                        apps = testData.shuffled()
                            .subList(0, Random.Default.nextInt(testData.size - 1))
                    )
                }
            }
        }
    }
}
