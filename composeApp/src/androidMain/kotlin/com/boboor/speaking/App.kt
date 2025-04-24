package com.boboor.speaking

import android.app.Application
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.boboor.speaking.di.initKoin
import com.boboor.speaking.ui.theme.AppTheme
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

