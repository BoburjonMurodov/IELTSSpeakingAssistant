package com.boboor.speaking.data.repository

import com.boboor.speaking.data.remote.models.PartOneResponse
import com.boboor.speaking.data.remote.models.PartThreeResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse


/*
    Created by Boburjon Murodov 20/02/25 at 11:01
*/

interface AppRepository {
    suspend fun getPartOneQuestions(): Result<PartOneResponse.Response>
    suspend fun getPartTwoQuestions(): Result<PartTwoResponse.Response>
    suspend fun getPartThreeQuestions(): Result<PartThreeResponse.Response>
}