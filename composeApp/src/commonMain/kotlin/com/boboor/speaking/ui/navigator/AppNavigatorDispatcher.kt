package com.boboor.speaking.ui.navigator

import AppNavigator
import AppScreen
import NavigationArgs
import NavigationHandler
import kotlinx.coroutines.flow.MutableSharedFlow


/*
    Created by Boburjon Murodov 20/12/24 at 20:03
*/

class AppNavigatorDispatcher : AppNavigator, NavigationHandler {
    override val screenState = MutableSharedFlow<NavigationArgs>()

    private suspend fun navigation(args: NavigationArgs) {
        screenState.emit(args)
    }

    override suspend fun back() {
        navigation { pop() }
    }

    override suspend fun push(screen: AppScreen, transition: TransitionType) {
        navigation { push(screen) }
    }

    override suspend fun popUntil(screen: AppScreen, transition: TransitionType) {
        navigation { this.popUntil { it == screen } }
    }

    override suspend fun replace(screen: AppScreen, transition: TransitionType) {
        navigation { replace(screen) }
    }

    override suspend fun replaceAll(screen: AppScreen, transition: TransitionType) {
        navigation { replaceAll(screen) }
    }

    override suspend fun backToRoot() {
        navigation { popUntilRoot() }
    }

}