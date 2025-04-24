package com.boboor.speaking.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.boboor.speaking.ui.theme.DuolingoTheme
import com.boboor.speaking.ui.theme.duoGray100Color


/*
    Created by Boburjon Murodov 24/04/25 at 15:09
*/


@Composable
fun BasicDuoLingoCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    colors: CardColors = CardColors(
        containerColor = duoGray100Color,
        contentColor = DuolingoTheme.colors.textColor,
        disabledContainerColor = DuolingoTheme.colors.cardBackground.copy(alpha = 0.5f),
        disabledContentColor = DuolingoTheme.colors.textColor.copy(alpha = 0.5f)
    ),
    content: @Composable () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    AnimatedClickEffect(value = 16f) { interactionSource, paddingValue, bottom ->
        Card(
            interactionSource = interactionSource,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
            modifier = modifier
                .graphicsLayer {
                    clip = false
                    this.translationY = paddingValue
                },
            colors = colors
        ) {
            Column(
                modifier = Modifier
                    .graphicsLayer {
//                        clip = false
                        this.translationY = bottom

                    }
//                    .padding(bottom = paddingValue.calculateBottomPadding())
            ) {
                content()
            }
        }
    }
}
