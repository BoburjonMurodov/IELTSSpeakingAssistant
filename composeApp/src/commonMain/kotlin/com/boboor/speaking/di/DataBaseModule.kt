package com.boboor.speaking.di

import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.data.local.LocalStorageImpl
import org.koin.dsl.module



val databaseModule = module {
//    single { get<AppDataBase>().commonTopicDao() }
//    single { get<AppDataBase>().partTwoTopicDao() }
    factory <LocalStorage>{ LocalStorageImpl() }
}

//expect fun platformModule(): Module