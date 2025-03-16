package com.boboor.speaking.di

import com.boboor.speaking.data.repository.AppRepository
import com.boboor.speaking.data.repository.impl.AppRepositoryImpl
import org.koin.dsl.module


/*
    Created by Boburjon Murodov 16/03/25 at 22:12
*/


val repositoryModule = module {
    single<AppRepository> { AppRepositoryImpl(get(), get()) }
}