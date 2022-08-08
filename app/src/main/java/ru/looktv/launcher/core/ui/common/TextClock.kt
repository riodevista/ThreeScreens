package ru.looktv.launcher.core.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun TextClock(
    modifier: Modifier = Modifier,
    textColor: Color,
    fontSize: TextUnit,
    format12Hour: String? = null,
    format24Hour: String? = null,
    timeZone: String? = null,
) {
    AndroidView(
        factory = { context ->
            android.widget.TextClock(context).apply {
                setTextColor(textColor.toArgb())
                textSize = fontSize.value
                format12Hour?.let { this.format12Hour = it }
                format24Hour?.let { this.format24Hour = it }
                timeZone?.let { this.timeZone = it }
            }
        },
        modifier = modifier
    )
}