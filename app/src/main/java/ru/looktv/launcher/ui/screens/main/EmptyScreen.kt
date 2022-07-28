package ru.looktv.launcher.ui.screens.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import ru.looktv.launcher.R

@Composable
fun EmptyScreen(modifier: Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        color = colorResource(id = R.color.white),
        fontSize = 28.sp
    )
}