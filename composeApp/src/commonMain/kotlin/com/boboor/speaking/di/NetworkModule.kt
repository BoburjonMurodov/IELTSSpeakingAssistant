package com.boboor.speaking.di

import com.boboor.speaking.createHttpClient
import com.boboor.speaking.data.remote.ApiService
import org.koin.dsl.module


/*
    Created by Boburjon Murodov 21/12/24 at 06:15
*/


val networkModule = module {
    single { ApiService(createHttpClient()) }
}