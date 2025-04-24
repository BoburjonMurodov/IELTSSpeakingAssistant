package com.boboor.speaking.di

import com.boboor.speaking.data.local.room.AppDataBase
import com.boboor.speaking.data.local.room.getAppDataBase
import com.boboor.speaking.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformModule(): Module = module {
    single<AppDataBase> {
        val builder = getDatabaseBuilder(context = get())
        getAppDataBase(builder)
    }
}