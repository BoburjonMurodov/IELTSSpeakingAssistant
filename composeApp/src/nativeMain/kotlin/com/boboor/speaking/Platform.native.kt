package com.boboor.speaking

import com.boboor.speaking.data.local.room.AppDataBase
import kotlinx.datetime.Clock
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import platform.posix.exit
import kotlin.system.exitProcess


//actual fun createHttpClient(): HttpClient = HttpClient(Darwin) {
//    install(ContentNegotiation) {
//        json(Json { ignoreUnknownKeys = true })
//    }
//}



actual object TimeUtil {
    actual fun systemTimeMs(): Long = NSDate().timeIntervalSince1970.toLong() * 1000
}

actual fun closeApp() {
    exit(0)
}

//actual fun provideAppDatabase(): AppDataBase {
//
//}