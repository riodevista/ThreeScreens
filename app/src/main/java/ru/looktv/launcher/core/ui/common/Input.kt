package ru.looktv.launcher.core.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Input(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    fillMaxWidth: Boolean = true,
    maxLength: Int? = null,
    text: String,
    onTextChange: (String) -> Unit,
    hint: String,
    focusRequester: FocusRequester? = null,
    fontSize: TextUnit? = null,
    letterSpacing: TextUnit? = null,
    fontFamily: FontFamily? = null,
    fontColor: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = MaterialTheme.colors.onPrimary,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    fontWeight: FontWeight? = null,
    onFocusChanged: ((FocusState) -> Unit)? = null,

) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            if (text.isEmpty()) {
                Text(
                    modifier = Modifier
                        .alpha(0.2F)
                        .align(Alignment.CenterStart),
                    text = hint,
                    fontSize = fontSize ?: 18.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = fontWeight
                )
            }
            val textSelectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.2F)
            )
            CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
                var textFieldModifier = if (fillMaxWidth) {
                    Modifier.fillMaxWidth()
                        .focusRequester(
                            focusRequester ?: remember { FocusRequester() }
                        )
                } else {
                    Modifier.width(IntrinsicSize.Min)
                        .focusRequester(
                            focusRequester ?: remember { FocusRequester() }
                        )
                }
                val onTextChangeBased = if (maxLength == null) {
                    onTextChange
                } else {
                    { onTextChange(it.cropIfNeeded(maxLength)) }
                }
                if (onFocusChanged != null) {
                    textFieldModifier = textFieldModifier.onFocusChanged(onFocusChanged)
                }
                BasicTextField(
                    modifier = textFieldModifier,
                    value = text,
                    visualTransformation = visualTransformation,
                    singleLine = singleLine,
                    textStyle = TextStyle(
                        color = fontColor,
                        fontSize = fontSize ?: 18.sp,
                        fontWeight = fontWeight,
                        letterSpacing = letterSpacing ?: TextUnit.Unspecified,
                        fontFamily = fontFamily,
                    ),
                    onValueChange = onTextChangeBased,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                )
            }
        }
    }
}

private fun String.cropIfNeeded(maxLength: Int): String {
    return if (length <= maxLength) {
        this
    } else {
        substring(0 until maxLength)
    }
}
