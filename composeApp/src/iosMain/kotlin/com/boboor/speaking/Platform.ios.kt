package com.boboor.speaking

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.request
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import platform.Foundation.NSString
import platform.Foundation.stringWithString

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenWidth(): Dp = LocalWindowInfo.current.containerSize.width.pxToPoint().dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenHeight(): Dp = LocalWindowInfo.current.containerSize.height.pxToPoint().dp

fun Int.pxToPoint(): Double = this.toDouble() / UIScreen.mainScreen.scale


actual fun createHttpClient(): HttpClient = HttpClient(Darwin) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }

    install(DefaultRequest) {
        val baseUrl = BASE_URl
        print("base url is $baseUrl")
        url {
//            protocol = URLProtocol.HTTPS
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://").removeSuffix("/")
            }
        }
    }



    // ✅ Response Interceptor: Observe Responses
    install(ResponseObserver) {
        request {
            println("✅ url: ${this.url}")
//            println("✅ request: ${it.content}")
        }
        onResponse { response ->
//            println("✅ url: ${response.call.request.url}")
            println("🔹 iOS: Received response: ${response.status}")

            // Example: Handle Unauthorized Responses
            if (response.status == HttpStatusCode.Unauthorized) {
                println("🚨 Unauthorized request detected!")
            }
        }
    }

}