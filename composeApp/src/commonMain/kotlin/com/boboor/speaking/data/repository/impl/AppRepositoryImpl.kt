package com.boboor.speaking.data.repository.impl

import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.data.remote.ApiService
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext


/*
    Created by Boburjon Murodov 16/03/25 at 20:51
*/

class AppRepositoryImpl(
    private val apiService: ApiService,
    private val localStorage: LocalStorage
) : AppRepository {
    override suspend fun getPartOneQuestions(fromCache: Boolean): CommonTopicResponse = withContext(Dispatchers.IO) {
        if (!fromCache) {
            val questions = apiService.getPartOneQuestions()
            localStorage.setPartOne(questions)
        }
        localStorage.getPartOne()!!
    }

    override suspend fun getPartTwoQuestions(fromCache: Boolean): CommonTopicResponse = withContext(Dispatchers.IO) {
        if (!fromCache) {
            val questions = apiService.getPartTwoQuestions()
//            localStorage.addPartOne(questions)
        }
        localStorage.getPartOne()!!
    }

    override suspend fun getPartThreeQuestions(fromCache: Boolean): CommonTopicResponse {
        if (!fromCache) {
            val questions = apiService.getPartThreeQuestions()
            localStorage.setPartThree(questions)
        }
        localStorage.getPartThree()!!
    }


}