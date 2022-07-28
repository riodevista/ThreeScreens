package ru.looktv.launcher.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.looktv.launcher.R
import ru.looktv.launcher.core.ui.common.dpadFocusable
import ru.looktv.launcher.core.ui.theme.Shapes
import ru.looktv.launcher.ui.view_models.LogoutScreenViewModel


@ExperimentalComposeUiApi
@Composable
fun LogoutScreen(
    openStartDestination: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: LogoutScreenViewModel = viewModel()
    viewModel.nextScreenEvent.collectAsState().run {
        value.getContentIfNotHandled()?.let {
            if (it) {
                openStartDestination()
            }
        }
    }
    viewModel.obtainUsername(context.applicationContext)
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .systemBarsPadding()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            modifier = Modifier.padding(30.dp),
            text = viewModel.username.collectAsState().value,
            color = MaterialTheme.colors.onBackground,
            fontSize = 20.sp
        )
        val clickAction = {
            viewModel.logout(context.applicationContext)
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .dpadFocusable(clickAction)
                .height(52.dp),
            onClick = clickAction,
            shape = Shapes.large
        ) {
            Text(text = stringResource(R.string.logout))
        }
    }
}