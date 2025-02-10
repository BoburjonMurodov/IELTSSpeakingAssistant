package com.boboor.speaking


import NavigationHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.boboor.speaking.ui.screens.main.MainScreen
import com.boboor.speaking.ui.screens.topic.TopicScreen
import com.boboor.speaking.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject


@Composable
@Preview
fun App( ) {
    AppTheme {
        KoinContext {
            val navigationHandler = koinInject<NavigationHandler>()

            Navigator(MainScreen(), onBackPressed = { true }) { navigator ->
                LaunchedEffect(Unit) { navigationHandler.screenState.collect { it.invoke(navigator) } }
                SlideTransition(navigator)
            }

        }

    }
}



