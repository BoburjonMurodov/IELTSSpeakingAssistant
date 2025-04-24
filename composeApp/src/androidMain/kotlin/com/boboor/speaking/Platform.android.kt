package com.boboor.speaking

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.boboor.speaking.data.local.room.AppDataBase
import com.boboor.speaking.utils.NativeLib
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


@Composable
actual fun getScreenWidth(): Dp = LocalConfiguration.current.screenWidthDp.dp

@Composable
actual fun getScreenHeight(): Dp = LocalConfiguration.current.screenHeightDp.dp


actual fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }

    engine {
        addInterceptor({ chain ->
            val request = chain.request().newBuilder()
                .build()

            chain.proceed(request)
        })
    }

    install(DefaultRequest) {
        val baseUrl = NativeLib.getBaseUrl()

        url {
            protocol = URLProtocol.HTTPS
            host = baseUrl.removeSuffix("/").removePrefix("https://").removePrefix("http://") // Set host
        }

    }

}

actual object TimeUtil {
    actual fun systemTimeMs(): Long = System.currentTimeMillis()
}

actual fun closeApp() {
    exitProcess(0)
}

