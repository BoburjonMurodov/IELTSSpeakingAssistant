package com.boboor.speaking.data.remote

import com.boboor.speaking.data.models.PartOneResponse
import com.boboor.speaking.data.models.PartThreeResponse
import com.boboor.speaking.data.models.PartTwoResponse
import com.boboor.speaking.utils.resultOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


/*
    Created by Boburjon Murodov 21/12/24 at 05:31
*/


class ApiService(private val httpClient: HttpClient) {
    private companion object {
        const val BASE_URL = "https://dbvirtualeducation.com/ielts/api/"
        const val SECTION_ONE = "section1"
        const val SECTION_TWO = "section2"
        const val SECTION_THREE = "section3"
    }

    suspend fun getPartOneQuestions(): Result<PartOneResponse.Response> {
        resultOf { httpClient.get("$BASE_URL$SECTION_ONE") }
            .onSuccess { return Result.success(it.body()) }
            .onFailure { return Result.failure(it) }
        return Result.failure(Exception("Unknown error"))
    }

    suspend fun getPartTwoQuestions(): Result<PartTwoResponse.Response> {
        resultOf { httpClient.get("$BASE_URL$SECTION_TWO") }
            .onSuccess { return Result.success(it.body()) }
            .onFailure { return Result.failure(it) }

        return Result.failure(Exception("Unknown error"))
    }

    suspend fun getPartThreeQuestions(): Result<PartThreeResponse.Response> {
        resultOf { httpClient.get("$BASE_URL$SECTION_THREE") }
            .onSuccess { return Result.success(it.body()) }
            .onFailure { return Result.failure(it) }

        return Result.failure(Exception("Unknown error"))
    }
}