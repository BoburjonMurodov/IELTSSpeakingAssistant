package com.boboor.speaking.data.models


sealed interface PartThreeResponse {
    data class Response(
        val content: Map<String, PartThreeQuestion>
    )

    data class PartThreeQuestion(
        val active: Boolean,
        val free: Boolean,
        val name: String,
        val new: String,
        val order: Int,
        val questions: List<Question>,
        val vocabulary: List<Vocabulary>
    )

    data class Question(
        val answer: List<Answer>,
        val ideas: List<Idea>,
        val text: String
    )


    data class Idea(
        val text: String
    )

    data class Vocabulary(
        val text: String
    )

    data class Answer(
        val text: String
    )
}