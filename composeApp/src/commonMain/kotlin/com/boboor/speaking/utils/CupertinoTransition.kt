package com.boboor.speaking.utils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator


/*
    Created by Boburjon Murodov 20/12/24 at 23:10
*/
@Composable
fun CustomCupertinoTransition(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = navigator.lastItem,
        transitionSpec = {
            ContentTransform(
                targetContentEnter = fadeIn() + slideInHorizontally(),
                initialContentExit = fadeOut() + slideOutHorizontally(),
            )
        }
    ) { screen ->
        screen.Content()
    }
}

