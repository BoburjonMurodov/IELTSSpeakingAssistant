package com.boboor.speaking.utils

import android.util.Log


/*
    Created by Boburjon Murodov 13/03/25 at 10:55
*/


object NativeLib {
    external fun getBaseUrl(): String

    init {
        System.loadLibrary("native-lib")
        Log.d("TTT", "the decompiled base url is ${getBaseUrl()}")
    }
}