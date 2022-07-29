package ru.looktv.launcher.core.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.looktv.launcher.R

@Composable
fun HorizontalTabs(
    modifier: Modifier = Modifier,
    titles: List<String>,
    positionOfSelected: Int,
    underlineColor: Color = colorResource(id = R.color.violet),
    titleColor: Color = colorResource(id = R.color.white),
    titleSize: TextUnit = 8.sp,
    tabWidth: Dp = 90.dp,
    onClick: (position: Int) -> Unit
) {
    LazyRow(modifier) {
        for (i in titles.indices) {
            val isSelected = positionOfSelected == i
            item {
                Box(
                    Modifier
                        .width(tabWidth)
                        .height(43.dp)
                        .clickable { onClick(i) }) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(bottom = 3.dp),
                        text = titles[i],
                        fontSize = titleSize,
                        color = titleColor,
                        fontWeight = FontWeight(600)
                    )
                    if (isSelected)
                        Box(
                            Modifier
                                .align(Alignment.BottomCenter)
                                .height(3.dp)
                                .fillMaxWidth()
                                .background(underlineColor)
                        )
                }
            }
        }
    }
}