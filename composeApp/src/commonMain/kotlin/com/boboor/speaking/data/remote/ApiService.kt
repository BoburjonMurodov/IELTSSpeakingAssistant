package com.boboor.speaking.data.remote

import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse
import com.boboor.speaking.data.remote.models.toCommonTopicResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.defaultTransformers
import io.ktor.client.plugins.plugin
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext


/*
    Created by Boburjon Murodov 21/12/24 at 05:31
*/


class ApiService(private val httpClient: HttpClient) {
    private companion object {
        const val SECTION_ONE = "section1"
        const val SECTION_TWO = "section2"
        const val SECTION_THREE = "section3"
    }


    suspend fun getPartOneQuestions(): CommonTopicResponse.Response  {
        return httpClient.get(SECTION_ONE).body();
    }

    suspend fun getPartTwoQuestions(): CommonTopicResponse.Response  {
        val response = httpClient.get(SECTION_TWO).body<PartTwoResponse.Response>()
        return response.toCommonTopicResponse()
    }

    suspend fun getPartThreeQuestions(): CommonTopicResponse.Response {
        return httpClient.get(SECTION_THREE).body()
    }
}