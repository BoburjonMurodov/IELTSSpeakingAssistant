package com.boboor.speaking

//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.boboor.speaking.data.local.room.AppDataBase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask


/*
    Created by Boburjon Murodov 21/04/25 at 18:20
*/




//fun getDatabaseBuilder(): RoomDatabase.Builder<AppDataBase> {
//    val dbFilePath = documentDirectory() + "/questions.db"
//    return Room.databaseBuilder<AppDataBase>(
//        name = dbFilePath,
//    )
//}

//@OptIn(ExperimentalForeignApi::class)
//private fun documentDirectory(): String {
//    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
//        directory = NSDocumentDirectory,
//        inDomain = NSUserDomainMask,
//        appropriateForURL = null,
//        create = false,
//        error = null,
//    )
//
//    return requireNotNull(documentDirectory?.path)
//}