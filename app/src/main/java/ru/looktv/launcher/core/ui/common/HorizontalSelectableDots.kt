package ru.looktv.launcher.core.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.looktv.launcher.R

@Composable
fun HorizontalSelectableDots(
    modifier: Modifier = Modifier,
    numberOfDots: Int,
    positionOfSelected: Int,
    background: Color = colorResource(id = R.color.white30),
    selected: Color = colorResource(id = R.color.white),
    size: Dp = 8.dp,
    spaceBetween: Dp = 8.dp,
    onClick: (position: Int) -> Unit
) {
    LazyRow(modifier, horizontalArrangement = Arrangement.spacedBy(spaceBetween)) {
        for (i in 0 until numberOfDots) {
            val isSelected = positionOfSelected == i
            item {
                Box(
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) selected else background,
                            shape = CircleShape
                        )
                        .clickable { onClick(i) },
                )
            }
        }
    }
}