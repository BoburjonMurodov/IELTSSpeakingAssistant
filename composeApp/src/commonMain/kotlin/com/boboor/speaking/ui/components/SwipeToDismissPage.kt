package com.boboor.speaking.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow


/*
    Created by Boburjon Murodov 15/03/25 at 14:22
*/

@Composable
fun SwipeToDismissPage(content: @Composable () -> Unit) {
    val navigator = LocalNavigator.currentOrThrow

    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "")

    val backColor by animateColorAsState(
        targetValue = if (animatedOffsetX < 250) MaterialTheme.colorScheme.surfaceContainerLow else MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
        label = ""
    )

    val iconTintColor by animateColorAsState(
        targetValue = if (animatedOffsetX < 250) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary,
        label = ""
    )


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(with(LocalDensity.current) {
                        (animatedOffsetX.toDp() - 24.dp).coerceIn(0.dp, 240.dp)
                    })
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(backColor),

                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "back",
                    tint = iconTintColor
                )
            }
        }
    }

    val dragEnabled = remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxHeight()
            .offset { IntOffset(animatedOffsetX.toInt(), 0) }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        dragEnabled.value = offset.x <= 100.dp.toPx() // Only allow if touch starts in first 200dp
                    }
                )
            }
            .draggable(
                state = rememberDraggableState { delta ->
                    if (dragEnabled.value) { // Only allow dragging if started within 200dp
                        val newOffset = (offsetX + delta).coerceIn(0f, 320f)
                        offsetX = newOffset
                    }
                },
                orientation = Orientation.Horizontal,
                onDragStopped = { velocity ->
                    if (dragEnabled.value) {
                        if (offsetX > 250f) {
                            navigator.pop()
                            offsetX = 0f
                        } else {
                            offsetX = 0f
                        }
                    }
                }
            )
    ) {
        content()
    }
}