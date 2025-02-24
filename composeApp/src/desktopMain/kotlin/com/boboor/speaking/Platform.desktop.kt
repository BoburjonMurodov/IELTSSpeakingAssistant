package com.boboor.speaking

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.awt.Toolkit
import kotlin.system.exitProcess

//@Composable
//actual fun getScreenWidth(): Dp {
//    return 0.dp
//}
//
//@Composable
//actual fun getScreenHeight(): Dp {
//    return 0.dp
//}


@Composable
actual fun getScreenWidth(): Dp {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val dpi = Toolkit.getDefaultToolkit().screenResolution
    return 400.dp // Convert pixels to dp
}


@Composable
actual fun getScreenHeight(): Dp {
    return 800.dp
}


actual fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}


actual object TimeUtil {
    actual fun systemTimeMs(): Long = System.currentTimeMillis()
}

actual fun closeApp() {
    exitProcess(0)
}

actual fun onTapStatusBar(onTapped: () -> Unit): () -> Unit {
    return {}
}

//actual class StatusBarTapListener {
//    actual fun onStatusBarTapped(callback: () -> Unit) {
//
//    }
//}