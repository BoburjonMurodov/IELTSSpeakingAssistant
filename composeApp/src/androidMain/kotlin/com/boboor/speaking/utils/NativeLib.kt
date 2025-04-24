package com.boboor.speaking.utils


/*
    Created by Boburjon Murodov 13/03/25 at 10:55
*/


object NativeLib {
    external fun getBaseUrl(): String

    init {
        try {
            System.loadLibrary("native-lib")
        } catch (e: Exception) {

        }
    }
}