package com.boboor.speaking


import NavigationHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.boboor.speaking.ui.pages.screens.splash.SplashScreen
import com.boboor.speaking.ui.theme.AppTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    AppTheme {
        KoinContext {
            val navigationHandler = koinInject<NavigationHandler>()
            Navigator(SplashScreen(), onBackPressed = { true }) { navigator ->
                LaunchedEffect(navigator) {
                    navigationHandler.screenState.collect { screenAction ->
                        screenAction.invoke(navigator)
                    }
                }
                SlideTransition(navigator)
            }
        }
    }
}