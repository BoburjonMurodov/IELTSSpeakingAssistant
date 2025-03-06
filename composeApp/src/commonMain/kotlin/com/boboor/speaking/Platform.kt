package com.boboor.speaking

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import io.ktor.client.HttpClient

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun getScreenWidth(): Dp

@Composable
expect fun getScreenHeight(): Dp

expect fun createHttpClient(): HttpClient


expect object TimeUtil {
    fun systemTimeMs(): Long
}

expect fun closeApp()
//expect fun onTapStatusBar(onTapped: () -> Unit): () -> Unit

//expect class StatusBarTapListener {
//    fun onStatusBarTapped(callback: () -> Unit)
//}
