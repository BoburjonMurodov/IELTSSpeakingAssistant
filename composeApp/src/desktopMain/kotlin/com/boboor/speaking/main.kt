package com.boboor.speaking

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.boboor.speaking.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "IELTS Speaking Assistant",
            resizable = false,
            state = WindowState(size = DpSize(400.dp, 800.dp))
        ) {
            App()
        }
    }
}
