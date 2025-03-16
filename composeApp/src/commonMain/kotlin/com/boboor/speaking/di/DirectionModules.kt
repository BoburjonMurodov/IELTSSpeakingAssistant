package com.boboor.speaking.di

import com.boboor.speaking.ui.pages.screens.splash.SplashScreenContracts
import com.boboor.speaking.ui.pages.screens.splash.SplashScreenDirections
import com.boboor.speaking.ui.pages.tabs.main.MainScreenContracts
import com.boboor.speaking.ui.pages.screens.topic.TopicScreenContracts
import com.boboor.speaking.ui.pages.tabs.main.MainScreenDirections
import com.boboor.speaking.ui.pages.screens.topic.TopicScreenDirections
import com.boboor.speaking.ui.pages.tabs.settings.SettingsContracts
import com.boboor.speaking.ui.pages.tabs.settings.SettingsTabDirections
import org.koin.dsl.module


/*
    Created by Boburjon Murodov 20/12/24 at 20:34
*/


val directionModule = module {
    factory<MainScreenContracts.Directions> { MainScreenDirections(get()) }
    factory<TopicScreenContracts.Directions> { TopicScreenDirections(get()) }
    factory<SettingsContracts.Directions> { SettingsTabDirections(get()) }
    factory<SplashScreenContracts.Directions> { SplashScreenDirections(get()) }
}