package ru.looktv.launcher.ui.screens.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import ru.looktv.launcher.ui.models.ContinueWatchingItem
import ru.looktv.launcher.ui.models.FavoriteItem
import ru.looktv.launcher.ui.models.PromoItem
import ru.looktv.launcher.ui.models.common.AppItem
import ru.looktv.launcher.ui.view_models.HomeScreenViewModel

private val NAV_BAR_WIDTH = 68.dp

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit
) {
    val viewModel: HomeScreenViewModel = viewModel()
    val screenModel = viewModel.screenModel.collectAsState()

    val context = LocalContext.current.applicationContext
    val packageManager = LocalContext.current.packageManager

    LaunchedEffect(Unit) {
        viewModel.loadAppsList(packageManager = packageManager)
    }

    TopBanner(
        modifier = Modifier,
        viewModel = viewModel,
        context = context,
        screenModel.value.time,
        screenModel.value.promos,
        screenModel.value.apps,
        screenModel.value.continues,
        screenModel.value.favorites,
        onProfileClick
    )
}

@Composable
fun TopBanner(
    modifier: Modifier,
    viewModel: HomeScreenViewModel,
    context: Context,
    time: String,
    promos: List<PromoItem>,
    apps: List<AppItem>,
    continues: List<ContinueWatchingItem>,
    favorites: List<FavoriteItem>,
    onProfileClick: () -> Unit,
) {
    val selectedItem = remember { mutableStateOf(0) }
    val image = promos[selectedItem.value].image
    val title = promos[selectedItem.value].title
    val subtitle = promos[selectedItem.value].subtitle
    LazyColumn(modifier) {
        item {
            Box() {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = image),
                    contentDescription = "top_film_banner",
                    contentScale = ContentScale.FillWidth
                )

                BannerOverlay(modifier = Modifier.height(340.dp))

                Column(
                    modifier = Modifier
                        .padding(top = 15.dp, start = NAV_BAR_WIDTH + 12.dp)

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
                            Text(
                                modifier = Modifier.padding(top = 3.dp),
                                text = time,
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.white)
                            )
                            CircleButton(
                                iconId = R.drawable.ic_profile,
                            ) {
                                onProfileClick()
                            }
                        }
                    }

                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = subtitle,
                        color = colorResource(id = R.color.white),
                        fontSize = 16.sp,
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp, end = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(width = 88.dp, height = 32.dp)
                                .focusable()
                                .clickable {},
                            painter = painterResource(id = R.drawable.ic_button_watch),
                            contentDescription = "to_watch",
                            contentScale = ContentScale.Fit
                        )
                        HorizontalSelectableDots(
                            numberOfDots = promos.size,
                            positionOfSelected = selectedItem.value
                        ) { position ->
                            selectedItem.value = position
                        }
                    }

                    Spacer(modifier = Modifier.height(43.dp))
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
                            AsyncImage(
                                modifier = Modifier
                                    .width(58.dp)
                                    .clip(RoundedCornerShape(CornerSize(16.dp)))
                                    .border(
                                        1.dp,
                                        colorResource(id = R.color.white30),
                                        RoundedCornerShape(CornerSize(16.dp))
                                    )
                                    .focusable()
                                    .clickable { viewModel.launchApp(context, it) },
                                model = it.icon,
                                contentDescription = "app_banner",
                                contentScale = ContentScale.Fit
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
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

                    Spacer(modifier = Modifier.height(16.dp))
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
                            Image(
                                modifier = Modifier
                                    .size(width = 207.dp, height = 116.dp)
                                    .clip(RoundedCornerShape(CornerSize(8.dp)))
                                    .focusable()
                                    .clickable { },
                                painter = painterResource(id = it.image),
                                contentDescription = "fav_banner",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
