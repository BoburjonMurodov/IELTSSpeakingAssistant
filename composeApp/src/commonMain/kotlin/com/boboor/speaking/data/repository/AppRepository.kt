package com.boboor.speaking.data.repository

import com.boboor.speaking.data.remote.models.CommonTopicResponse


/*
    Created by Boburjon Murodov 20/02/25 at 11:01
*/

interface AppRepository {
    suspend fun getPartOneQuestions(fromCache: Boolean = true): CommonTopicResponse
    suspend fun getPartTwoQuestions(fromCache: Boolean = true): CommonTopicResponse
    suspend fun getPartThreeQuestions(fromCache: Boolean = true): CommonTopicResponse
}