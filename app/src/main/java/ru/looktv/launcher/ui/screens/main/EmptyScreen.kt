package ru.looktv.launcher.ui.screens.main

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.looktv.launcher.R

@Composable
fun EmptyScreen(modifier: Modifier, text: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        text = text,
        color = colorResource(id = R.color.white),
        fontSize = 28.sp,
        textAlign = TextAlign.Center
    )
}