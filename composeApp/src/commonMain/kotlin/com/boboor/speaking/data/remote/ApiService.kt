package com.boboor.speaking.data.remote

import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


/*
    Created by Boburjon Murodov 21/12/24 at 05:31
*/


class ApiService(private val httpClient: HttpClient) {
    private companion object {
        const val SECTION_ONE = "section1"
        const val SECTION_TWO = "section2"
        const val SECTION_THREE = "section3"
    }
    suspend fun getPartOneQuestions(): CommonTopicResponse.Response = httpClient.get(SECTION_ONE).body()
    suspend fun getPartTwoQuestions(): PartTwoResponse.Response = httpClient.get(SECTION_TWO).body()
    suspend fun getPartThreeQuestions(): CommonTopicResponse.Response = httpClient.get(SECTION_THREE).body()
}
