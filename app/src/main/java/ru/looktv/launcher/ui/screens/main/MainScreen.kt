package ru.looktv.launcher.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.looktv.launcher.R
import ru.looktv.launcher.core.ui.common.SelectableCircleButton
import ru.looktv.launcher.ui.view_models.MainScreenViewModel

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = viewModel()
    val selectedButton = remember { mutableStateOf(0) }
    Box(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .systemBarsPadding()
            .background(colorResource(id = R.color.background))
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            when (selectedButton.value) {
                0 -> HomeScreen()
                1 -> AppsScreen()
                else -> EmptyScreen(
                    Modifier.align(Alignment.Center),
                    text = "Скоро здесь что-то будет"
                )
            }
        }

        NavigationBar(
            modifier = Modifier.align(Alignment.CenterStart), selectedButton = selectedButton
        )

    }
}

@Composable
fun NavigationBar(modifier: Modifier, selectedButton: MutableState<Int>) {
    Box(
        modifier
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.dark80))
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 11.dp, vertical = 26.dp)
        ) {
            Image(
                modifier = Modifier
                    .width(46.dp)
                    .align(Alignment.TopCenter),
                painter = painterResource(id = R.drawable.ic_main_screen_top_logo),
                contentDescription = "main_screen_top_logo"
            )
        }
        TvLookButtons(Modifier.align(Alignment.Center), selectedButton)
    }
}

@Composable
fun TvLookButtons(modifier: Modifier, selectedButton: MutableState<Int>) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SelectableCircleButton(
            iconId = R.drawable.ic_home,
            isSelected = selectedButton.value == 0
        ) {
            selectedButton.value = 0
        }
        SelectableCircleButton(
            iconId = R.drawable.ic_movie,
            isSelected = selectedButton.value == 1
        ) {
            selectedButton.value = 1
        }
        SelectableCircleButton(
            iconId = R.drawable.ic_apps,
            isSelected = selectedButton.value == 2
        ) {
            selectedButton.value = 2
        }
        SelectableCircleButton(
            iconId = R.drawable.ic_language,
            isSelected = selectedButton.value == 3
        ) {
            selectedButton.value = 3
        }
    }
}
