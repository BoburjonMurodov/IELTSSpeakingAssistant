package com.boboor.speaking.di

import AppNavigator
import NavigationHandler
import com.boboor.speaking.ui.navigator.AppNavigatorDispatcher
import org.koin.dsl.bind
import org.koin.dsl.module


/*
    Created by Boburjon Murodov 20/12/24 at 20:25
*/


val appNavigatorModule = module {
    single { AppNavigatorDispatcher() }
    single<AppNavigator> { get<AppNavigatorDispatcher>() }
    single<NavigationHandler> { get<AppNavigatorDispatcher>() }
}