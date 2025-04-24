package com.boboor.speaking.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.boboor.speaking.data.local.room.entities.PartTwoQuestion
import com.boboor.speaking.data.local.room.entities.PartTwoTopic
import com.boboor.speaking.data.local.room.entities.PartTwoTopicWithContent
import com.boboor.speaking.data.local.room.entities.PartTwoVocabulary


/*
    Created by Boburjon Murodov 21/04/25 at 17:52
*/


@Dao
interface PartTwoTopicDao {

    // Query
    @Transaction
    @Query("SELECT * FROM part_two_topic")
    suspend fun getAllTopicsWithContent(): List<PartTwoTopicWithContent>

    @Transaction
    @Query("SELECT * FROM part_two_topic WHERE id = :topicId")
    suspend fun getTopicWithContent(topicId: String): PartTwoTopicWithContent?

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<PartTwoTopic>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<PartTwoQuestion>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabulary(vocabulary: List<PartTwoVocabulary>)

    // Update
    @Update
    suspend fun updateTopic(topic: PartTwoTopic)

    @Update
    suspend fun updateQuestion(question: PartTwoQuestion)
}