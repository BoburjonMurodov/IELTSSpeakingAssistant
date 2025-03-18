package com.boboor.speaking.data.repository.impl

import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.data.remote.ApiService
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse
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
    override suspend fun getPartOneQuestions(fromCache: Boolean): List<CommonTopicResponse.Topic> = withContext(Dispatchers.IO) {
        if (!fromCache) {
            val questions = apiService.getPartOneQuestions()
            val questionList = questions.content.map { it.value }
            localStorage.setPartOne(questionList)
        }
        localStorage.getPartOne()
    }

    override suspend fun getPartTwoQuestions(fromCache: Boolean): List<PartTwoResponse.Topic> = withContext(Dispatchers.IO) {
        if (!fromCache) {
            val questions = apiService.getPartTwoQuestions()
            val questionList = questions.content.map { it.value }
            localStorage.setPartTwo(questionList)
        }
        localStorage.getPartTwo()
    }

    override suspend fun getPartThreeQuestions(fromCache: Boolean): List<CommonTopicResponse.Topic> = withContext(Dispatchers.IO) {
        if (!fromCache) {
            val questions = apiService.getPartThreeQuestions()
            val questionList = questions.content.map { it.value }
            localStorage.setPartThree(questionList)
        }
        localStorage.getPartThree()
    }


}