package com.boboor.speaking

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.boboor.speaking.data.local.room.AppDataBase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.security.SecureRandom


/*
    Created by Boburjon Murodov 21/04/25 at 18:19
*/


fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDataBase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("questions.db")
    val passphrase = SQLiteDatabase.getBytes("test".toCharArray())
    Log.d("TTT", "getDatabaseBuilder: ")
//    val passphrase = "test"
    return Room.databaseBuilder<AppDataBase>(
        context = appContext,
        name = dbFile.absolutePath,
    )

//        .openHelperFactory(SupportFactory(passphrase))
}