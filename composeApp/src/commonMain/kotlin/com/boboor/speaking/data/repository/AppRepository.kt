package com.boboor.speaking.data.repository

import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse


/*
    Created by Boburjon Murodov 20/02/25 at 11:01
*/

interface AppRepository {
    suspend fun getPartOneQuestions(fromCache: Boolean = true): List<CommonTopicResponse.Topic>
    suspend fun getPartTwoQuestions(fromCache: Boolean = true): List<PartTwoResponse.Topic>
    suspend fun getPartThreeQuestions(fromCache: Boolean = true): List<CommonTopicResponse.Topic>
}