package ru.looktv.launcher.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.looktv.launcher.R
import ru.looktv.launcher.ui.view_models.SplashViewModel


@Composable
fun SplashScreen(
    openNextScreen: () -> Unit
) {
    val viewModel: SplashViewModel = viewModel()
    viewModel.nextScreenEvent.collectAsState().run {
        value.getContentIfNotHandled()?.let {
            if (it) {
                openNextScreen()
            }
        }
    }
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .systemBarsPadding()
            .background(colorResource(id = R.color.background))
            .padding(horizontal = 42.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier.weight(1f),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "splash_screen_logo"
        )
        Spacer(Modifier.weight(1f))
        LinearProgressIndicator(color = Color(0xFF8073FF), backgroundColor = Color(0x308073FF))
        Spacer(Modifier.height(42.dp))
    }
}