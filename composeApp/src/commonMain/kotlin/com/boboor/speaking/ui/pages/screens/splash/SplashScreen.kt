package com.boboor.speaking.ui.pages.screens.splash

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.transitions.ScreenTransition
import com.boboor.speaking.utils.collectAsState
import com.boboor.speaking.utils.darken
import com.boboor.speaking.utils.koinScreenModel
import kotlinx.coroutines.delay


/*
    Created by Boburjon Murodov 22/02/25 at 02:24
*/

@OptIn(ExperimentalVoyagerApi::class)
class SplashScreen : Screen, ScreenTransition {

    override fun exit(lastEvent: StackEvent): ExitTransition? {
        return fadeOut()
    }

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<SplashScreenContracts.ViewModel>()
        val state = viewModel.collectAsState()
        SplashScreenContent(state, viewModel::onEventDispatcher)
    }
 }


@Composable
private fun SplashScreenContent(
    state: State<SplashScreenContracts.UIState>,
    onEventDispatcher: (SplashScreenContracts.Intent) -> Unit = {}
) {
    val scale = Animatable(1f)
    val alpha = Animatable(0f)

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(500))
        scale.animateTo(1.7f, animationSpec = spring(stiffness = Spring.StiffnessVeryLow))
        delay(1000)
        onEventDispatcher.invoke(SplashScreenContracts.Intent.Init)
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
                .scale(scale.value)
                .alpha(alpha.value)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
            ) {
                val gradientBrush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary.darken(0.5f)
                    ),
                )

                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                                fontWeight = FontWeight.Black,
                                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                            )
                        ) { append("IELTS\n") }

                        withStyle(
                            style = SpanStyle(
                                fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                                fontWeight = FontWeight.Black,
                                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                            )
                        ) { append("Speaking") }
                    },
                    style = TextStyle(brush = gradientBrush),
                    textAlign = TextAlign.Center
                )
            }
        }

        AnimatedContent(state.value.isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(Modifier.weight(2f))
                CircularProgressIndicator()
                Spacer(Modifier.weight(1f))
            }
        }

    }

}