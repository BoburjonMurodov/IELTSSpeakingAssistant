package com.boboor.speaking.di

import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.data.local.LocalStorageImpl
import org.koin.dsl.module


val localStorageImplModule = module {
    factory <LocalStorage>{ LocalStorageImpl() }
}