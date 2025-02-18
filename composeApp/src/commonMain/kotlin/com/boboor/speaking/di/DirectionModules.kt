package com.boboor.speaking.di

import androidx.compose.ui.modifier.modifierLocalMapOf
import com.boboor.speaking.presenter.main.MainScreenContracts
import com.boboor.speaking.presenter.topic.TopicScreenContracts
import com.boboor.speaking.ui.screens.main.MainScreenDirections
import com.boboor.speaking.ui.screens.topic.TopicScreenDirections
import org.koin.dsl.module


/*
    Created by Boburjon Murodov 20/12/24 at 20:34
*/


val directionModule = module {
    factory<MainScreenContracts.Directions> { MainScreenDirections(get()) }
    factory<TopicScreenContracts.Directions> { TopicScreenDirections(get()) }
}