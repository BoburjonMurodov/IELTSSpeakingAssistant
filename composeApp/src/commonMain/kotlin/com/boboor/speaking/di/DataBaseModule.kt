package com.boboor.speaking.di

import com.boboor.speaking.data.local.LocalStorage
import org.koin.dsl.module


val localStorageModule = module {
    single { LocalStorage() }
}