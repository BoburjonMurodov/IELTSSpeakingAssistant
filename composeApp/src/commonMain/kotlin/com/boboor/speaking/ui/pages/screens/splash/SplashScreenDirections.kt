package com.boboor.speaking.ui.pages.screens.splash

import AppNavigator
import com.boboor.speaking.ui.pages.HomeScreen


/*
    Created by Boburjon Murodov 16/03/25 at 22:22
*/

class SplashScreenDirections(private val appNavigator: AppNavigator) : SplashScreenContracts.Directions {
    override suspend fun navigateHomeScreen() {
        appNavigator.replace(HomeScreen())
    }
}