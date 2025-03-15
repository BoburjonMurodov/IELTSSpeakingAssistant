package com.boboor.speaking.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CancellationException


/*
    Created by Boburjon Murodov 20/02/25 at 10:47
*/


inline fun <T, R> T.resultOf(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun Color.darken(factor: Float = 0.8f): Color {
    return Color(
        red = (red * factor).coerceIn(0f, 1f),
        green = (green * factor).coerceIn(0f, 1f),
        blue = (blue * factor).coerceIn(0f, 1f),
        alpha = alpha
    )
}

fun Color.gradient(): Brush {
    return Brush.linearGradient(
        colors = listOf(
            this,
            this.copy(alpha = 0.8f).darken(0.5f)
        )
    )
}