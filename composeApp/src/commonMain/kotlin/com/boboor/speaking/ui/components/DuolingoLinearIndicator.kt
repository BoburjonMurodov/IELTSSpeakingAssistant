package com.boboor.speaking.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/*
    Created by Boburjon Murodov 25/04/25 at 00:22
*/

@Composable
fun DuolingoLinearIndicator(
    modifier: Modifier = Modifier,
    currentProgress: Float,
    color: Color,
    backgroundColor: Color,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
    ) {
        val width = size.width
        val barHeight = size.height

        drawLine(
            color = backgroundColor,
            start = Offset(0f, barHeight / 2),
            end = Offset(width, barHeight / 2),
            strokeWidth = barHeight,
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(0f, barHeight / 2),
            end = Offset(currentProgress.coerceIn(0f, 1f) * width, barHeight / 2),
            strokeWidth = barHeight,
            cap = StrokeCap.Square
        )
    }
}