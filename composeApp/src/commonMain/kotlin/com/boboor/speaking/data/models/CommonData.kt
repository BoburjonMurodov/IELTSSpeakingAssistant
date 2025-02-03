package com.boboor.speaking.data.models

import kotlinx.serialization.Serializable


sealed interface CommonData{
    @Serializable
    data class Response(
        val content: Map<String, Topic>
    ) : CommonData


    @Serializable
    data class Topic(
        val active: Boolean,
        val free: Boolean,
        val name: String,
        val new: String,
        val order: Int,
        val questions: List<Question>,
        val vocabulary: List<Vocabulary>
    ) : CommonData


    @Serializable
    data class Answer(
        val text: String
    ) : CommonData

    @Serializable
    data class Idea(
        val text: String
    ) : CommonData

    @Serializable
    data class Question(
        val answer: List<Answer>,
        val ideas: List<Idea>,
        val text: String
    ) : CommonData

    @Serializable
    data class Vocabulary(
        val text: String
    ) : CommonData
}

