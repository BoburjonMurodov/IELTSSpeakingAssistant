package com.boboor.speaking.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.abs


/*
    Created by Boburjon Murodov 17/03/25 at 22:00
*/


@Composable
fun ScalableHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    beyondViewportPageCount: Int = 0,
    content: @Composable (page: Int) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        beyondViewportPageCount = 1
    ) { page ->
        val scrollProgress = abs(pagerState.currentPageOffsetFraction).coerceAtMost(1f)
        val cornerRadius = (64.dp * (abs(scrollProgress).coerceIn(0f, 1f)))

        val scale = when {
            scrollProgress <= 0.5f -> lerp(1f, 0.9f, scrollProgress * 2f)
            else -> 0.9f
        }
        val borderWidth = when {
            scrollProgress <= 0.5f -> lerp(0f, 1f, scrollProgress * 2f)
            else -> 0f
        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .then(
                    if (scrollProgress == 0.0f) {
                        Modifier
                    } else Modifier
                        .border(
                            border = BorderStroke(borderWidth.dp, color = MaterialTheme.colorScheme.outlineVariant),
                            shape = RoundedCornerShape(cornerRadius)
                        )
                )
                .clip(RoundedCornerShape(cornerRadius))
        ) {
            content(page)
        }
    }
}

