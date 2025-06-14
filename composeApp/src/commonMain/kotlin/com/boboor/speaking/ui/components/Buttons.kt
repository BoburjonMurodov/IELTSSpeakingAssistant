package com.boboor.speaking.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boboor.speaking.ui.theme.DuolingoTheme
import kotlinx.coroutines.delay


/*
    Created by Boburjon Murodov 24/04/25 at 12:36
*/


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(16.dp),
    onClick: () -> Unit
) {
    BottomLine(
        lineColor = DuolingoTheme.colors.duoGreenHover,
        lineHeight = 8.dp,
        borderRadius = 16.dp,
    ) { interactionSource ->
        Button(
            shape = shape,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = DuolingoTheme.colors.duoGreen,
                disabledContainerColor = DuolingoTheme.colors.duoGreen.copy(alpha = 0.5f),
                contentColor = Color.White,
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            ),
            enabled = enabled,
            interactionSource = interactionSource,
            onClick = { onClick() }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = text,
                style = DuolingoTheme.typography.subHeading.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun BottomLine(
    modifier: Modifier = Modifier,
    borderRadius: Dp = 16.dp,
    lineHeight: Dp = 8.dp,
    lineColor: Color,
    crossinline onClick: () -> Unit = {},
    crossinline content: @Composable (MutableInteractionSource) -> Unit,
) {
    val contentHeight = remember { mutableStateOf(0f) }
    val contentWidth = remember { mutableStateOf(0f) }
    val borderRadiusPx = with(LocalDensity.current) { borderRadius.toPx() }
    val bottomLineThickness = with(LocalDensity.current) { lineHeight.toPx() }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = remember { mutableStateOf(false) }
    val offsetY = animateFloatAsState(
        if (isPressed.value) bottomLineThickness else 0f,
        animationSpec = tween(durationMillis = 100)
    )

    LaunchedEffect(Unit) {
        interactionSource.interactions.collect {
            when (it) {
                is PressInteraction.Press -> {
                    isPressed.value = true
                }

                else -> {
                    delay(100)
                    isPressed.value = false
                    onClick()
                }
            }
        }
    }

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        Layout(
            modifier = modifier,
            content = {
                Canvas(modifier = Modifier) {
                    drawRoundRect(
                        color = lineColor,
                        topLeft = Offset(0f, contentHeight.value / 2),
                        cornerRadius = CornerRadius(borderRadiusPx, borderRadiusPx),
                        size = Size(
                            width = contentWidth.value,
                            height = contentHeight.value / 2 + bottomLineThickness
                        )
                    )
                }

                content(interactionSource)
            }
        ) { measurables, constraints ->
            val placables = measurables.map { it.measure(constraints) }


            val canvasPlaceable = placables[0]
            val buttonPlaceable = placables[1]


            contentHeight.value = buttonPlaceable.height.toFloat()
            contentWidth.value = buttonPlaceable.width.toFloat()

            val maxHeight = placables.sumOf { it.height }
            val maxWidth = placables.sumOf { it.width }

            layout(width = maxWidth, height = maxHeight + bottomLineThickness.toInt()) {
                canvasPlaceable.place(position = IntOffset(0, 0))
                buttonPlaceable.place(position = IntOffset(0, offsetY.value.toInt()))
            }
        }
    }
}
