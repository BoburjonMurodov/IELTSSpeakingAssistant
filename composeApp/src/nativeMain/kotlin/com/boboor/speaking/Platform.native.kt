package com.boboor.speaking

import kotlinx.datetime.Clock
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970


//actual fun createHttpClient(): HttpClient = HttpClient(Darwin) {
//    install(ContentNegotiation) {
//        json(Json { ignoreUnknownKeys = true })
//    }
//}



actual object TimeUtil {
    actual fun systemTimeMs(): Long = NSDate().timeIntervalSince1970.toLong() * 1000
}