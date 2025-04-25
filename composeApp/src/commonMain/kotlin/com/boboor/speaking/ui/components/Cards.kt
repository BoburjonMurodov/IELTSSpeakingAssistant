package com.boboor.speaking.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.boboor.speaking.ui.theme.DuolingoTheme
import com.boboor.speaking.ui.theme.duoGray100Color


/*
    Created by Boburjon Murodov 24/04/25 at 15:09
*/


@Composable
fun BasicDuoLingoCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    colors: CardColors = CardColors(
        containerColor = DuolingoTheme.colors.background,
        contentColor = DuolingoTheme.colors.textColor,
        disabledContainerColor = DuolingoTheme.colors.cardBackground,
        disabledContentColor = DuolingoTheme.colors.textColor,
    ),
    content: @Composable () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    BottomLine(
        borderRadius = 24.dp,
        lineHeight = 4.dp,
        lineColor = duoGray100Color,
    ) {
        OutlinedCard(
            border = BorderStroke(2.dp, duoGray100Color),
            shape = RoundedCornerShape(24.dp),
            interactionSource = it,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
//            modifier = modifier,
            colors = colors
        ) {
            Column(
                modifier = modifier,
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
            ) {
                content()
            }
        }
    }
}
