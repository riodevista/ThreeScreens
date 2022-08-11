package ru.looktv.launcher.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.looktv.launcher.R
import ru.looktv.launcher.domain.AppsInteractor
import ru.looktv.launcher.ui.models.common.AppItemModel
import ru.looktv.launcher.ui.models.common.UpdateStatus
import ru.looktv.launcher.utils.LauncherUtils

@Composable
fun AppView(
    appItemModel: AppItemModel
) {
    val appContext = LocalContext.current.applicationContext
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(CornerSize(2.dp)))
            .clickable {
                when (appItemModel.updateStatus ?: UpdateStatus.NO_UPDATES) {
                    UpdateStatus.NEW_VERSION_IS_READY -> {
                        AppsInteractor.launchAppDownloading(appContext, appItemModel.name)
                    }
                    UpdateStatus.DOWNLOADING_NOW -> {
                        // Do nothing
                    }
                    UpdateStatus.READY_TO_INSTALL -> {
                        AppsInteractor.launchAppInstalling(appContext, appItemModel.name)
                    }
                    else -> {
                        LauncherUtils.startApp(appContext, appItemModel.packageName)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .width(58.dp)
                .clip(RoundedCornerShape(CornerSize(16.dp))),
            model = appItemModel.icon,
            contentDescription = "${appItemModel.name}_app_icon",
            contentScale = ContentScale.FillWidth,
        )

        LaunchedEffect(key1 = appItemModel.updateStatus) {
            when (appItemModel.updateStatus ?: UpdateStatus.NO_UPDATES) {
                UpdateStatus.NEW_VERSION_IS_READY -> {
                    AppsInteractor.launchAppDownloading(appContext, appItemModel.name)
                }
                UpdateStatus.DOWNLOADING_NOW -> {
                    // Just wait
                }
                UpdateStatus.READY_TO_INSTALL -> {
                    AppsInteractor.launchAppInstalling(
                        appContext,
                        appItemModel.name
                    )
                }
                else -> {
                    LauncherUtils.startApp(appContext, appItemModel.packageName)
                }
            }
        }

        UpdateIcon(appItemModel)
    }
}

@Composable
private fun UpdateIcon(appItemModel: AppItemModel) {
    when (appItemModel.updateStatus ?: UpdateStatus.NO_UPDATES) {
        UpdateStatus.NEW_VERSION_IS_READY -> {
            Image(
                modifier = Modifier.padding(all = 4.dp),
                painter = painterResource(id = R.drawable.ic_new_app_version),
                contentDescription = "new_app_version",
                alpha = 0.75f
            )
        }
        UpdateStatus.DOWNLOADING_NOW -> {
            CircularProgressIndicator(color = Color(0xFF8073FF))
        }
        UpdateStatus.READY_TO_INSTALL -> {
            Image(
                modifier = Modifier.padding(all = 4.dp),
                painter = painterResource(id = R.drawable.ic_update_is_ready_to_install),
                contentDescription = "new_app_version",
                alpha = 0.75f
            )
        }
        else -> {}
    }
}
