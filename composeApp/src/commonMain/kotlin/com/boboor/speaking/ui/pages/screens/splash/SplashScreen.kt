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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.transitions.ScreenTransition
import com.boboor.speaking.ui.theme.DuolingoTheme
import com.boboor.speaking.utils.collectAsState
import com.boboor.speaking.utils.darken
import com.boboor.speaking.utils.koinScreenModel
import kotlinx.coroutines.delay
import uz.gita.boburmobilebankingapp.ui.compontents.VerticalSpacer


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
//        SplashScreenContent(state, viewModel::onEventDispatcher)
        ScreenContent()
    }

    @Composable
    private fun ScreenContent() {
        Scaffold(
            containerColor = DuolingoTheme.colors.duoBlue
        ) { innerPadding ->
            innerPadding
            val indicatorWidth = remember { mutableStateOf(0f) }
            Box(
                modifier = Modifier.fillMaxSize()
                    .navigationBarsPadding()
                    .statusBarsPadding()
                    .padding(24.dp),
            ) {

                StackedResponsiveTitle(
                    modifier = Modifier.align(Alignment.Center),
                    title = "IELTS",
                    content = "ASSISTANT",
                    onWidthCalculated = {
                        indicatorWidth.value = it.value
                    }
                )


                Column(
                    modifier = Modifier.align(Alignment.BottomCenter)
                        .padding(bottom = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LinearProgressIndicator(
                        modifier = Modifier.width(indicatorWidth.value.dp)
                            .height(8.dp),
                        trackColor = DuolingoTheme.colors.duoGreen.copy(alpha = 0.3f),
                        color = DuolingoTheme.colors.duoGreen,
                        gapSize = 12.dp
                    )

                    VerticalSpacer(12.dp)

                    Text(
                        text = "Updating content...",
                        style = DuolingoTheme.typography.bodySmall.copy(color = DuolingoTheme.colors.secondaryTextColor)
                    )
                }
            }
        }
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


@Composable
private fun StackedResponsiveTitle(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onWidthCalculated: (Dp) -> Unit = {}
) {
    SubcomposeLayout(modifier) { constraints ->
        val ieltsMeasurables = subcompose("title") {
            Text(
                text = title,
                style = DuolingoTheme.typography.title.copy(
                    fontSize = 100.sp,
                    color = Color.White
                )
            )
        }

        val ieltsPlaceable = ieltsMeasurables.first().measure(constraints)
        val ieltsWidth = ieltsPlaceable.width

        val assistantFontSize = 50.sp
        val assistantMeasurables = subcompose("content") {
            Text(
                text = content,
                style = DuolingoTheme.typography.title.copy(
                    fontSize = assistantFontSize,
                    color = Color.White
                )
            )
        }
        val assistantPlaceableUnscaled = assistantMeasurables.first().measure(constraints)
        val assistantWidth = assistantPlaceableUnscaled.width


        val scale = ieltsWidth.toFloat() / assistantWidth.toFloat()
        val scaledAssistantFontSize = assistantFontSize * scale

        val finalAssistantPlaceable = subcompose("content_scaled") {
            Text(
                text = content,
                style = DuolingoTheme.typography.title.copy(
                    fontSize = scaledAssistantFontSize,
                    color = Color.White
                )
            )
        }.first().measure(constraints)

        val height = ieltsPlaceable.height + finalAssistantPlaceable.height

        layout(ieltsWidth, height) {
            onWidthCalculated(with(density) { ieltsWidth.toDp() })

            var y = 0
            ieltsPlaceable.placeRelative(0, y)
            y += ieltsPlaceable.height
            finalAssistantPlaceable.placeRelative(0, y)
        }
    }
}

