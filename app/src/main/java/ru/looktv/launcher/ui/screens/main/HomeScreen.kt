package ru.looktv.launcher.ui.screens.main

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.looktv.launcher.R
import ru.looktv.launcher.core.ui.common.BannerOverlay
import ru.looktv.launcher.core.ui.common.CircleButton
import ru.looktv.launcher.core.ui.common.HorizontalSelectableDots
import ru.looktv.launcher.core.ui.common.TextClock
import ru.looktv.launcher.ui.models.ContinueWatchingItem
import ru.looktv.launcher.ui.models.FavoriteItem
import ru.looktv.launcher.ui.models.HomeScreenModel
import ru.looktv.launcher.ui.models.PromoItem
import ru.looktv.launcher.ui.models.common.AppItemModel
import ru.looktv.launcher.ui.screens.common.AppView
import ru.looktv.launcher.ui.view_models.HomeScreenViewModel

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit
) {
    val viewModel: HomeScreenViewModel = viewModel()
    val screenModel = viewModel.screenModel.collectAsState()
    val promos = screenModel.value.promos
    val apps = screenModel.value.apps
    val continues = screenModel.value.continues
    val favorites = screenModel.value.favorites

    val context = LocalContext.current.applicationContext
    val packageManager = LocalContext.current.packageManager

    LaunchedEffect(Unit) {
        viewModel.loadAppsList(packageManager = packageManager)
    }

    val selectedItem = remember { mutableStateOf(0) }
    val image = if (promos.isNotEmpty()) promos[selectedItem.value].image else null
    val title = if (promos.isNotEmpty()) promos[selectedItem.value].title else ""
    val subtitle = if (promos.isNotEmpty()) promos[selectedItem.value].subtitle else ""

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        item {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp),
                    model = image,
                    contentDescription = "top_film_banner",
                    contentScale = ContentScale.Crop
                )

                BannerOverlay(modifier = Modifier.height(340.dp))

                AnimatedVisibility(visible = screenModel.value.showPromoProgress) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp)
                    ) {
                        CircularProgressIndicator(
                            Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 100.dp),
                            color = colorResource(id = R.color.violet)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 15.dp, start = 12.dp)

                ) {
                    TitleRow(title, screenModel, onProfileClick)

                    Subtitle(subtitle)

                    WatchButtonAndSelectableDotsRow(promos, selectedItem)

                    Spacer(modifier = Modifier.height(43.dp))
                    AppsList(apps, viewModel, context)

                    Spacer(modifier = Modifier.height(16.dp))
                    ContinueWatchingList(continues)

                    Spacer(modifier = Modifier.height(16.dp))
                    FavoritesList(favorites)

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun TitleRow(
    title: String,
    screenModel: State<HomeScreenModel>,
    onProfileClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(end = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = colorResource(id = R.color.white),
            fontSize = 48.sp
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TextClock(
                modifier = Modifier.padding(top = 3.dp),
                fontSize = 16.sp,
                textColor = colorResource(id = R.color.white)
            )
            CircleButton(
                iconId = R.drawable.ic_profile,
            ) {
                onProfileClick()
            }
        }
    }
}

@Composable
private fun Subtitle(subtitle: String) {
    Text(
        modifier = Modifier.alpha(0.5f),
        text = subtitle,
        color = colorResource(id = R.color.white),
        fontSize = 16.sp,
    )
}

@Composable
private fun WatchButtonAndSelectableDotsRow(
    promos: List<PromoItem>,
    selectedItem: MutableState<Int>
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, end = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(CornerSize(2.dp)))
                .clickable { }
        ) {
            Image(
                modifier = Modifier
                    .size(width = 88.dp, height = 32.dp),
                painter = painterResource(id = R.drawable.ic_button_watch),
                contentDescription = "to_watch",
                contentScale = ContentScale.Fit
            )
        }

        HorizontalSelectableDots(
            numberOfDots = promos.size,
            positionOfSelected = selectedItem.value
        ) { position ->
            selectedItem.value = position
        }
    }
}

@Composable
private fun AppsList(
    apps: List<AppItemModel>,
    viewModel: HomeScreenViewModel,
    context: Context
) {
    Text(
        text = "Приложения",
        color = colorResource(id = R.color.white50),
        fontSize = 16.sp
    )
    Spacer(modifier = Modifier.height(8.dp))

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(9.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(apps) {
            AppView(it)
        }
    }
}

@Composable
private fun ContinueWatchingList(continues: List<ContinueWatchingItem>) {
    Text(
        text = "Продолжить просмотр",
        color = colorResource(id = R.color.white50),
        fontSize = 16.sp
    )
    Spacer(modifier = Modifier.height(8.dp))

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(continues) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(CornerSize(8.dp)))
                    .clickable { }
            ) {
                Image(
                    modifier = Modifier
                        .size(width = 207.dp, height = 116.dp)
                        .clip(RoundedCornerShape(CornerSize(8.dp)))
                        .focusable()
                        .clickable { },
                    painter = painterResource(id = it.image),
                    contentDescription = "continue_banner",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
private fun FavoritesList(favorites: List<FavoriteItem>) {
    Text(
        text = "Мой лист",
        color = colorResource(id = R.color.white50),
        fontSize = 16.sp
    )
    Spacer(modifier = Modifier.height(8.dp))

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(favorites) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(CornerSize(8.dp)))
                    .clickable { }
            ) {
                Image(
                    modifier = Modifier
                        .size(width = 207.dp, height = 116.dp)
                        .clip(RoundedCornerShape(CornerSize(8.dp))),
                    painter = painterResource(id = it.image),
                    contentDescription = "fav_banner",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
