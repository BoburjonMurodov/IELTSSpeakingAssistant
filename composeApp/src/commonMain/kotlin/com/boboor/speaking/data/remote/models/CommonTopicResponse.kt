package com.boboor.speaking.data.remote.models

import kotlinx.serialization.Serializable


/*
    Created by Boburjon Murodov 20/02/25 at 14:58
*/


sealed interface CommonTopicResponse {
    @Serializable
    data class Response(
        val content: Map<String, Topic>
    ) : CommonTopicResponse

    @Serializable
    data class Topic(
//        val type: TopicType, // Distinguishes Part 1, 2, 3
        val active: Boolean,
        val free: Boolean,
        val name: String,
        val new: String,
        val order: Int,
        val questions: List<Question>,
        val vocabulary: List<Vocabulary>
    ) : CommonTopicResponse

    @Serializable
    data class Question(
        val answer: List<Answer>,
        val ideas: List<Idea>,
        val text: String
    ) : CommonTopicResponse

    @Serializable
    data class Answer(
        val text: String
    ) : CommonTopicResponse

    @Serializable
    data class Idea(
        val text: String
    ) : CommonTopicResponse

    @Serializable
    data class Vocabulary(
        val text: String
    ) : CommonTopicResponse
}