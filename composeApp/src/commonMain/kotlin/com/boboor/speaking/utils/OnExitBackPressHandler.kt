package com.boboor.speaking.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.boboor.speaking.closeApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/*
    Created by Boburjon Murodov 22/02/25 at 13:56
*/



@OptIn(InternalVoyagerApi::class)
@Composable
fun OnExitBackPressHandler(onBackPressed: suspend () -> Unit) {
    var shouldExit by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = true) {
        if (shouldExit) {
            closeApp()
        } else {
            coroutineScope.launch {
                onBackPressed()
            }
            shouldExit = true

            coroutineScope.launch {
                delay(5000)
                shouldExit = false
            }
        }
    }
}
