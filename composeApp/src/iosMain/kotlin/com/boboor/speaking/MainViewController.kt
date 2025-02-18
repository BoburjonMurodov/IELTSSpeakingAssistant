package com.boboor.speaking

import androidx.compose.ui.window.ComposeUIViewController
import com.boboor.speaking.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) {
    App()
}
