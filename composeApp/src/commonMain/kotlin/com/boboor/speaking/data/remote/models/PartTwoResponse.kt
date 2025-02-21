package com.boboor.speaking.data.remote.models

import kotlinx.serialization.Serializable


/*
    Created by Boburjon Murodov 21/12/24 at 21:34
*/



sealed interface PartTwoResponse {
    @Serializable
    data class Response(
        val content: Map<String, PartTwoQuestion>
    ) : PartTwoResponse

    @Serializable
    data class PartTwoQuestion(
        val active: Boolean,
        val answer: List<Answer>,
        val free: Boolean,
        val ideas: List<Idea>,
        val name: String,
        val new: String,
        val order: Int,
        val questions: List<Question>,
        val vocabulary: List<Vocabulary>
    ) : PartTwoResponse

    @Serializable
    data class Answer(
        val text: String
    ) : PartTwoResponse

    @Serializable
    data class Question(
        val text: String
    ) : PartTwoResponse

    @Serializable
    data class Vocabulary(
        val text: String
    ) : PartTwoResponse


    @Serializable
    data class PartTwoBody(
        val text: String
    ) : PartTwoResponse


    @Serializable
    data class Idea(
        val body: List<PartTwoBody>,
        val text: String
    ) : PartTwoResponse
}










