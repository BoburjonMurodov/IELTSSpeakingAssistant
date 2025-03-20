package com.boboor.speaking.ui.navigator

import AppNavigator
import AppScreen
import NavigationArgs
import NavigationHandler
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import com.boboor.speaking.ui.pages.screens.splash.SplashScreen
import kotlinx.coroutines.flow.MutableSharedFlow


/*
    Created by Boburjon Murodov 20/12/24 at 20:03
*/

class AppNavigatorDispatcher : AppNavigator, NavigationHandler {
    override val screenState = MutableSharedFlow<NavigationArgs>()

    private suspend fun navigation(args: NavigationArgs) {
        screenState.emit(args)
    }

    override suspend fun back() = navigation { pop() }
    override suspend fun push(screen: AppScreen) = navigation { push(screen) }
    override suspend fun popUntil(screen: AppScreen) = navigation { this.popUntil { it == screen } }
    override suspend fun replace(screen: AppScreen) = navigation { replace(screen) }
    @OptIn(InternalVoyagerApi::class)
    override suspend fun replaceAll(screen: AppScreen) = navigation {
        dispose(SplashScreen())
        replaceAll(screen) }
    override suspend fun backToRoot() = navigation { popUntilRoot() }
}