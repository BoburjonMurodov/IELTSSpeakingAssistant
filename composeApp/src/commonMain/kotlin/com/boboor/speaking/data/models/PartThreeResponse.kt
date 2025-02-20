package com.boboor.speaking.data.models

import kotlinx.serialization.Serializable


sealed interface PartThreeResponse {
    @Serializable
    data class Response(
        val content: Map<String, PartThreeQuestion>
    )

    @Serializable
    data class PartThreeQuestion(
        val active: Boolean,
        val free: Boolean,
        val name: String,
        val new: String,
        val order: Int,
        val questions: List<Question>,
        val vocabulary: List<Vocabulary>
    )

    @Serializable
    data class Question(
        val answer: List<Answer>,
        val ideas: List<Idea>,
        val text: String
    )

    @Serializable
    data class Idea(
        val text: String
    )

    @Serializable
    data class Vocabulary(
        val text: String
    )

    @Serializable
    data class Answer(
        val text: String
    )
}