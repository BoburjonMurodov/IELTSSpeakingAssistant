package com.boboor.speaking

import android.app.Application
import com.boboor.speaking.di.initKoin
import com.boboor.speaking.utils.NativeLib
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger


/*
    Created by Boburjon Murodov 20/12/24 at 19:37
*/



class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApp)
            androidLogger()

        }

        NativeLib.getBaseUrl()

        multiplatform.network.cmptoast.AppContext.apply { set(applicationContext) }
    }
}