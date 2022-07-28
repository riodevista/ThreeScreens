package ru.looktv.launcher.core.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.looktv.launcher.R

@Composable
fun SelectableCircleButton(
    iconId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    iconOffset: Dp = 6.dp,
    backgroundColor: Color = colorResource(id = R.color.white30),
    iconColor: Color = colorResource(id = R.color.white),
    selectedBackgroundColor: Color = colorResource(id = R.color.white),
    selectedIconColor: Color = colorResource(id = R.color.dark80),
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                if (isSelected) selectedBackgroundColor else backgroundColor,
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable { onClick?.invoke() }
    ) {
        Image(
            modifier = Modifier.padding(iconOffset),
            painter = painterResource(iconId),
            colorFilter = ColorFilter.tint(if (isSelected) selectedIconColor else iconColor),
            contentDescription = "icon_circle_button_$iconId",
        )
    }

}
