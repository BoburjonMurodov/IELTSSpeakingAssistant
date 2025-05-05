package com.boboor.speaking.data.local.room.dao

//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Transaction
//import androidx.room.Update
import com.boboor.speaking.data.local.room.entities.CommonQuestion
import com.boboor.speaking.data.local.room.entities.CommonTopic
import com.boboor.speaking.data.local.room.entities.CommonTopicWithContent
import com.boboor.speaking.data.local.room.entities.CommonVocabulary


/*
    Created by Boburjon Murodov 21/04/25 at 17:51
*/

//@Dao
interface CommonTopicDao {

    // Fetching
//    @Transaction
//    @Query("SELECT * FROM common_topic")
    suspend fun getAllTopicsWithContent(): List<CommonTopicWithContent>

//    @Transaction
//    @Query("SELECT * FROM common_topic WHERE id = :topicId")
    suspend fun getTopicWithContent(topicId: String): CommonTopicWithContent?

    // Insert or Replace (doesn't touch progress/bookmark)
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<CommonTopic>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<CommonQuestion>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabulary(vocabulary: List<CommonVocabulary>)

    // Update only progress/bookmark
//    @Update
    suspend fun updateTopic(topic: CommonTopic)

//    @Update
    suspend fun updateQuestion(question: CommonQuestion)

    // Optional: clear everything
//    @Query("DELETE FROM common_topic")
    suspend fun clearTopics()
}