package com.boboor.speaking.data.remote

import com.boboor.speaking.data.models.PartOneResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


/*
    Created by Boburjon Murodov 21/12/24 at 05:31
*/

class ApiService(private val httpClient: HttpClient) {
    suspend fun getPartOneQuestions(): Result<PartOneResponse.Response> {
        try {
            val response: PartOneResponse.Response = httpClient.get("https://dbvirtualeducation.com/ielts/api/section1").body()

            response.content.forEach {
                println(it.key)
            }
            return Result.success(response)
        } catch (e: Exception) {
            println(e.message)

            return Result.failure(e)
        }

    }
}