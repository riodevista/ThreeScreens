package ru.looktv.launcher.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.looktv.launcher.R
import ru.looktv.launcher.core.ui.common.BannerOverlay
import ru.looktv.launcher.core.ui.common.CircleButton

private val NAV_BAR_WIDTH = 68.dp

@Composable
fun HomeScreen() {
    Box {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.dummy_film_banner_big),
            contentDescription = "top_film_banner",
            contentScale = ContentScale.FillWidth
        )
        BannerOverlay()
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 15.dp, start = NAV_BAR_WIDTH + 12.dp, end = 32.dp)

        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "The Boys",
                    color = colorResource(id = R.color.white),
                    fontSize = 48.sp
                )
                CircleButton(
                    iconId = R.drawable.ic_profile,
                ) {
                }
            }

            Text(
                modifier = Modifier.alpha(0.5f),
                text = "Сезон 2",
                color = colorResource(id = R.color.white),
                fontSize = 16.sp,
            )
        }

    }
}
