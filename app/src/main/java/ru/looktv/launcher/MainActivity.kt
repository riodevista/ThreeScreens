package ru.looktv.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.looktv.launcher.core.ui.theme.ThreeScreensTheme
import ru.looktv.launcher.domain.UserRepository

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDirection = if (UserRepository.getUsername(applicationContext).isNotBlank())
            Destinations.MAIN_SCREEN
        else
            Destinations.SPLASH
        setContent {
            Application(startDirection)
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        lifecycleScope.launch {
            delay(50)
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    @OptIn(ExperimentalAnimationApi::class)
    fun Application(startDirection: String) {
        val navController = rememberAnimatedNavController()
        ThreeScreensTheme {
            ThreeScreensNavHost(
                navController,
                startDirection
            )
        }
    }
}
