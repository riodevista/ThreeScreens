package ru.looktv.launcher

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import ru.looktv.launcher.ui.screens.LoginScreen
import ru.looktv.launcher.ui.screens.LogoutScreen
import ru.looktv.launcher.ui.screens.SplashScreen
import ru.looktv.launcher.ui.screens.WelcomeScreen
import ru.looktv.launcher.ui.screens.main.MainScreen

internal object Destinations {
    const val BACK = "back"
    const val LOGIN = "login"
    const val LOGOUT = "logout"
    const val MAIN_SCREEN = "main_screen"
    const val SPLASH = "splash"
    const val WELCOME = "welcome"
}

@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ThreeScreensNavHost(
    navController: NavHostController,
    startDirection: String
) {
    Box {
        AnimatedNavHost(
            navController = navController,
            startDestination = startDirection
        ) {
            composable(Destinations.SPLASH) {
                SplashScreen(
                    openNextScreen = {
                        navController.popBackStack()
                        navController.navigate(Destinations.WELCOME)
                    }
                )
            }
            composable(Destinations.WELCOME) {
                WelcomeScreen(
                    openNextScreen = {
                        navController.popBackStack()
                        navController.navigate(Destinations.MAIN_SCREEN)
                    }
                )
            }
            composable(Destinations.LOGIN) {
                LoginScreen(
                    openMainScreen = {
                        navController.popBackStack()
                        navController.navigate(Destinations.LOGOUT)
                    }
                )
            }
            composable(Destinations.MAIN_SCREEN) {
                MainScreen(
                )
            }
            composable(Destinations.LOGOUT) {
                LogoutScreen(
                    openStartDestination = {
                        navController.popBackStack()
                        navController.navigate(Destinations.SPLASH)
                    }
                )
            }
        }
    }
}

@Composable
fun BoxWithOffset(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
        ) {
            Box(modifier = Modifier)
        }
        Card(
            Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .align(Alignment.BottomCenter),
            backgroundColor = MaterialTheme.colors.background,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            content()
        }
    }
}

