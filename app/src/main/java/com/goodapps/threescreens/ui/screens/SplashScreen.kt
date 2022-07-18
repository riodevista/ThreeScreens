package com.goodapps.threescreens.ui.screens

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
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goodapps.threescreens.R
import com.goodapps.threescreens.ui.view_models.SplashViewModel


@Composable
fun SplashScreen(
    openLoginScreen: () -> Unit
) {
    val viewModel: SplashViewModel = viewModel()
    viewModel.nextScreenEvent.collectAsState().run {
        value.getContentIfNotHandled()?.let {
            if (it) {
                openLoginScreen()
            }
        }
    }
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .systemBarsPadding()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 42.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "splash_screen_logo"
        )
        Spacer(Modifier.height(42.dp))
        LinearProgressIndicator()
    }
}