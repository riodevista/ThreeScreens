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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.looktv.launcher.R
import ru.looktv.launcher.core.ui.common.SelectableCircleButton
import ru.looktv.launcher.ui.view_models.MainScreenViewModel

internal object Destinations {
    const val PROFILE = 0
    const val APPS = 1
    const val HOME = 2
//    const val MOVIES = 3
    const val LANGUAGE = 4
}

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = viewModel()
    val selectedButton = remember { mutableStateOf(Destinations.HOME) }
    val selectedScreen = remember { mutableStateOf(Destinations.HOME) }
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
            when (selectedScreen.value) {
                Destinations.HOME -> HomeScreen() { selectedScreen.value = Destinations.PROFILE }
                Destinations.APPS -> AppsScreen() { selectedScreen.value = Destinations.PROFILE }
                Destinations.PROFILE -> ProfileScreen() {
                    selectedScreen.value = selectedButton.value
                }
                else -> EmptyScreen(
                    Modifier.align(Alignment.Center),
                    text = stringResource(R.string.coming_soon)
                )
            }
        }

        NavigationBar(Modifier.align(Alignment.CenterStart), selectedButton, selectedScreen)
    }
}

@Composable
fun NavigationBar(
    modifier: Modifier,
    selectedButton: MutableState<Int>,
    selectedScreen: MutableState<Int>
) {
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
        TvLookButtons(Modifier.align(Alignment.Center), selectedButton, selectedScreen)
    }
}

@Composable
fun TvLookButtons(
    modifier: Modifier,
    selectedButton: MutableState<Int>,
    selectedScreen: MutableState<Int>
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SelectableCircleButton(
            iconId = R.drawable.ic_home,
            isSelected = selectedButton.value == Destinations.HOME
        ) {
            selectedButton.value = Destinations.HOME
            selectedScreen.value = Destinations.HOME
        }
//        SelectableCircleButton(
//            iconId = R.drawable.ic_movie,
//            isSelected = selectedButton.value == Destinations.MOVIES
//        ) {
//            selectedButton.value = Destinations.MOVIES
//            selectedScreen.value = Destinations.MOVIES
//        }
        SelectableCircleButton(
            iconId = R.drawable.ic_apps,
            isSelected = selectedButton.value == Destinations.APPS
        ) {
            selectedButton.value = Destinations.APPS
            selectedScreen.value = Destinations.APPS
        }
        SelectableCircleButton(
            iconId = R.drawable.ic_language,
            isSelected = selectedButton.value == Destinations.LANGUAGE
        ) {
            selectedButton.value = Destinations.LANGUAGE
            selectedScreen.value = Destinations.LANGUAGE
        }
    }
}
