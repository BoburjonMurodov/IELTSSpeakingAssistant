package com.boboor.speaking.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay


/*
    Created by Boburjon Murodov 24/04/25 at 15:05
*/

private const val ANIMATION_DURATION = 80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedClickEffect(
    value: Float = 4f,
    content: @Composable (interactionSource: MutableInteractionSource, padding: Float, offsetY: Float) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }
    val animatedBottom = animateFloatAsState(
        targetValue = if (isPressed) 0f else value,
        animationSpec = tween(durationMillis = ANIMATION_DURATION)
    )

    val animatedTop = animateFloatAsState(
        targetValue = if (isPressed) value else 0f,
        animationSpec = tween(durationMillis = ANIMATION_DURATION)
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isPressed = true
                }

                is PressInteraction.Release, is PressInteraction.Cancel -> {
                    delay(ANIMATION_DURATION.toLong())
                    isPressed = false
                }
            }
        }
    }

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        content(interactionSource, animatedBottom.value, animatedTop.value)
    }
}