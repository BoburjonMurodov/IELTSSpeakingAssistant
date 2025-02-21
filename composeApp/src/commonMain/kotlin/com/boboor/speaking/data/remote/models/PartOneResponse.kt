package com.boboor.speaking.data.remote.models

import kotlinx.serialization.Serializable


sealed interface PartOneResponse{
    @Serializable
    data class Response(
        val content: Map<String, Topic>
    ) : PartOneResponse


    @Serializable
    data class Topic(
        val active: Boolean,
        val free: Boolean,
        val name: String,
        val new: String,
        val order: Int,
        val questions: List<Question>,
        val vocabulary: List<Vocabulary>
    ) : PartOneResponse


    @Serializable
    data class Answer(
        val text: String
    ) : PartOneResponse

    @Serializable
    data class Idea(
        val text: String
    ) : PartOneResponse

    @Serializable
    data class Question(
        val answer: List<Answer>,
        val ideas: List<Idea>,
        val text: String
    ) : PartOneResponse

    @Serializable
    data class Vocabulary(
        val text: String
    ) : PartOneResponse

}

