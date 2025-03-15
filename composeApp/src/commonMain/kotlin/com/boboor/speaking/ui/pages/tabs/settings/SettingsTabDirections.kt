package com.boboor.speaking.ui.pages.tabs.settings

import AppNavigator
import com.boboor.speaking.ui.pages.screens.license.LicenseScreen
import com.boboor.speaking.ui.pages.screens.splash.SplashScreen


/*
    Created by Boburjon Murodov 12/03/25 at 16:55
*/

class SettingsTabDirections(private val navigator: AppNavigator) : SettingsContracts.Directions {
    override suspend fun goBackToSplashScreen() {
        navigator.replaceAll(SplashScreen())
    }

    override suspend fun goLicenseScreen() {
        navigator.push(LicenseScreen())
    }
}