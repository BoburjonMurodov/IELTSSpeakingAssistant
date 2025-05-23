package com.boboor.speaking.di

import com.boboor.speaking.ui.pages.screens.splash.SplashScreenContracts
import com.boboor.speaking.ui.pages.screens.splash.SplashScreenVM
import com.boboor.speaking.ui.pages.tabs.main.MainScreenContracts
import com.boboor.speaking.ui.pages.tabs.main.MainScreenVM
import com.boboor.speaking.ui.pages.screens.topic.TopicScreenContracts
import com.boboor.speaking.ui.pages.screens.topic.TopicScreenVM
import com.boboor.speaking.ui.pages.tabs.main.MainScreenDirections
import com.boboor.speaking.ui.pages.tabs.settings.SettingsContracts
import com.boboor.speaking.ui.pages.tabs.settings.SettingsScreenVM
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


/*
    Created by Boburjon Murodov 20/12/24 at 19:54
*/


val mainScreenModule = module {
    factory<MainScreenContracts.ViewModel> { MainScreenVM(get()) }
    factory<TopicScreenContracts.ViewModel> { TopicScreenVM(get(), get(), get() ) }
    factory<SettingsContracts.ViewModel> { SettingsScreenVM(get(), get()) }
    factory<SplashScreenContracts.ViewModel> { SplashScreenVM(get(), get()) }
}