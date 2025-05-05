@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.boboor.speaking.data.local.room

//import androidx.room.ConstructedBy
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import androidx.room.RoomDatabaseConstructor
//import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.boboor.speaking.data.local.room.dao.CommonTopicDao
import com.boboor.speaking.data.local.room.dao.PartTwoTopicDao
import com.boboor.speaking.data.local.room.entities.CommonQuestion
import com.boboor.speaking.data.local.room.entities.CommonTopic
import com.boboor.speaking.data.local.room.entities.CommonVocabulary
import com.boboor.speaking.data.local.room.entities.PartTwoQuestion
import com.boboor.speaking.data.local.room.entities.PartTwoTopic
import com.boboor.speaking.data.local.room.entities.PartTwoVocabulary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

//@Database(
//    entities = [
//        CommonTopic::class,
//        CommonQuestion::class,
//        CommonVocabulary::class,
//        PartTwoTopic::class,
//        PartTwoQuestion::class,
//        PartTwoVocabulary::class
//    ], version = 1
//)

//@ConstructedBy(AppDataBase::class)
abstract class AppDataBase
//    : RoomDatabase()
{
    abstract fun commonTopicDao(): CommonTopicDao
    abstract fun partTwoTopicDao(): PartTwoTopicDao
}
//
//
//@Suppress("NO_ACTUAL_FOR_EXPECT")
//expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDataBase> {
//    override fun initialize(): AppDataBase
//}
//
//
//fun getAppDataBase(builder: RoomDatabase.Builder<AppDataBase>): AppDataBase {
//    return builder
//        .setDriver(BundledSQLiteDriver())
//        .setQueryCoroutineContext(Dispatchers.IO)
//        .build()
//}