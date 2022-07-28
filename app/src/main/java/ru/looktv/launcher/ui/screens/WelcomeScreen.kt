package ru.looktv.launcher.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.looktv.launcher.R
import ru.looktv.launcher.ui.view_models.WelcomeViewModel


@Composable
fun WelcomeScreen(
    openNextScreen: () -> Unit
) {
    val viewModel: WelcomeViewModel = viewModel()
    viewModel.nextScreenEvent.collectAsState().run {
        value.getContentIfNotHandled()?.let {
            if (it) {
                openNextScreen()
            }
        }
    }
    Box(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .systemBarsPadding()
            .background(colorResource(id = R.color.background))
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 60.dp, y = 20.dp),
            painter = painterResource(id = R.drawable.ellipse_green),
            contentDescription = "bkg_ellipse_green"
        )
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-100).dp),
            painter = painterResource(id = R.drawable.ellipse_purple),
            contentDescription = "bkg_ellipse_green"
        )
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 42.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
            Text(text = "Привет!", fontSize = 32.sp, color = Color.White)
            Spacer(Modifier.weight(1f))
        }
    }

}