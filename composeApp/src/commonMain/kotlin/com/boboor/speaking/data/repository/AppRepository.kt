package com.boboor.speaking.data.repository

import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse


/*
    Created by Boburjon Murodov 20/02/25 at 11:01
*/

interface AppRepository {
    suspend fun fetchAllTopics()
    suspend fun updatePartOneQuestions(fromCache: Boolean = false): List<CommonTopicResponse.Topic>
    suspend fun updatePartTwoQuestions(fromCache: Boolean = false): List<PartTwoResponse.Topic>
    suspend fun updatePartThreeQuestions(fromCache: Boolean = false): List<CommonTopicResponse.Topic>
}