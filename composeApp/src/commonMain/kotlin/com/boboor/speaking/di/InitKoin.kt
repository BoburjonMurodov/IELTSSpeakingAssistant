package com.boboor.speaking.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


/*
    Created by Boburjon Murodov 20/12/24 at 19:32
*/


fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appNavigatorModule, mainScreenModule, directionModule, networkModule)
    }
}