package com.boboor.speaking.data.models

import kotlinx.serialization.Serializable


/*
    Created by Boburjon Murodov 21/12/24 at 21:34
*/



sealed interface PartTwoData {
    @Serializable
    data class Response(
        val content: Map<String, PartTwoQuestion>
    ) : PartTwoData


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
    ) : PartTwoData

    @Serializable
    data class Answer(
        val text: String
    ) : PartTwoData

    @Serializable
    data class Question(
        val text: String
    ) : PartTwoData

    @Serializable
    data class Vocabulary(
        val text: String
    ) : PartTwoData


    @Serializable
    data class PartTwoBody(
        val text: String
    ) : PartTwoData


    @Serializable
    data class Idea(
        val body: List<PartTwoBody>,
        val text: String
    ) : PartTwoData
}










