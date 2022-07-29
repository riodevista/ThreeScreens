package ru.looktv.launcher.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.looktv.launcher.R
import ru.looktv.launcher.core.ui.common.CircleButton
import ru.looktv.launcher.core.ui.common.HorizontalTabs
import ru.looktv.launcher.ui.view_models.AppsScreenViewModel

private val NAV_BAR_WIDTH = 68.dp

@Composable
fun AppsScreen() {
    val viewModel: AppsScreenViewModel = viewModel()
    val screenModel = viewModel.screenModel.collectAsState()

    val selectedTabItem = remember { mutableStateOf(0) }

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .alpha(0.6f),
            painter = painterResource(id = R.drawable.apps_background),
            contentDescription = "background",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(top = 26.5.dp, start = NAV_BAR_WIDTH + 12.dp)
        ) {
            // Топ строка
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.apps),
                    color = colorResource(id = R.color.white),
                    fontSize = 32.sp
                )
                CircleButton(
                    iconId = R.drawable.ic_profile,
                ) {
                }
            }
            Spacer(modifier = Modifier.height(26.dp))

            // Табы
            HorizontalTabs(
                titles = screenModel.value.tabs,
                positionOfSelected = selectedTabItem.value,
                onClick = {
                    selectedTabItem.value = it
                    viewModel.filter(it)
                }
            )

            // Сетка
            Box() {
                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(top = 26.dp, end = 32.dp, bottom = 32.dp),
                    columns = GridCells.Adaptive(minSize = 99.dp)
                ) {
                    items(screenModel.value.apps + screenModel.value.apps) {
                        Image(
                            modifier = Modifier
                                .size(width = 99.dp, height = 58.dp)
                                .clip(RoundedCornerShape(CornerSize(8.dp)))
                                .focusable()
                                .clickable { },
                            painter = painterResource(id = it.image),
                            contentDescription = "app_banner",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(26.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x4D1D1A3C),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }
    }
}

