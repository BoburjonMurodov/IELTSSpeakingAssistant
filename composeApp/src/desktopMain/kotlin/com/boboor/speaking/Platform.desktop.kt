package com.boboor.speaking

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.awt.Toolkit
import java.io.File
import java.io.FileWriter
import kotlin.system.exitProcess


@Composable
actual fun getScreenWidth(): Dp {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val dpi = Toolkit.getDefaultToolkit().screenResolution
    return 400.dp
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


