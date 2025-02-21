package com.boboor.speaking.di

import com.boboor.speaking.presenter.main.MainScreenContracts
import com.boboor.speaking.presenter.main.MainScreenVM
import com.boboor.speaking.presenter.topic.TopicScreenContracts
import com.boboor.speaking.presenter.topic.TopicScreenVM
import org.koin.dsl.module


/*
    Created by Boburjon Murodov 20/12/24 at 19:54
*/


val mainScreenModule = module {
    factory<MainScreenContracts.ViewModel> { MainScreenVM(get()) }
    factory<TopicScreenContracts.ViewModel> { TopicScreenVM(get(), get(), get()) }
}