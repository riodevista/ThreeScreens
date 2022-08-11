package ru.looktv.launcher.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.looktv.launcher.BuildConfig
import ru.looktv.launcher.domain.AppsInteractor
import ru.looktv.launcher.ui.models.MainScreenModel

class MainScreenViewModel() : ViewModel() {

    private val appsUpdateInteractor = AppsInteractor

    private val _screenModel =
        MutableStateFlow(
            MainScreenModel(
                showNewLauncherVersionDialog = false,
                showUpdateDownloadingDialog = false
            )
        )
    val screenModel: StateFlow<MainScreenModel> get() = _screenModel

    init {
        checkForUpdates()
    }

    private fun showLauncherUpdateDialog() {
        _screenModel.update {
            it.copy(
                showNewLauncherVersionDialog = true,
                showUpdateDownloadingDialog = false
            )
        }
    }

    fun confirmUpdate(appContext: Context) {
        _screenModel.update {
            it.copy(
                showNewLauncherVersionDialog = false,
                showUpdateDownloadingDialog = true
            )
        }
        appsUpdateInteractor.launchAppDownloading(appContext = appContext, "launcher")
    }

    fun dismissUpdateDialog() {
        _screenModel.update {
            it.copy(showNewLauncherVersionDialog = false)
        }
    }

    private fun checkForUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                appsUpdateInteractor.getUpdatesList()
                    .let { updateList ->
                        val launcherApk = updateList.find { it.name == "launcher" }
                        launcherApk?.let {
                            val currentVersion = BuildConfig.VERSION_CODE
                            if (launcherApk.version > currentVersion) {
                                showLauncherUpdateDialog()
                            }
                        }
                    }
            } catch (e: Exception) {
                // Silently do nothing or log error
            }
        }
    }
}
