package com.boboor.speaking.data.remote.models

import kotlinx.serialization.Serializable


/*
    Created by Boburjon Murodov 21/12/24 at 21:34
*/



sealed interface PartTwoResponse {
    @Serializable
    data class Response(
        val content: Map<String, Topic>
    ) : PartTwoResponse

    @Serializable
    data class Topic(
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

fun PartTwoResponse.Response.toCommonTopicResponse(): CommonTopicResponse.Response {
    return CommonTopicResponse.Response(
        content = content.mapValues { (_, partTwoQuestion) ->
            partTwoQuestion.toTopic()
        }
    )
}

fun PartTwoResponse.Topic.toTopic(): CommonTopicResponse.Topic {
    return CommonTopicResponse.Topic(
        active = this.active,
        free = this.free,
        name = this.name,
        new = this.new,
        order = this.order,
        vocabulary = this.vocabulary.map { it.toVocabulary() },
        questions = this.questions.map { it.toCommonQuestion(this.answer, this.ideas) }
    )
}

fun PartTwoResponse.Question.toCommonQuestion(
    answers: List<PartTwoResponse.Answer>,
    ideas: List<PartTwoResponse.Idea>
): CommonTopicResponse.Question {
    return CommonTopicResponse.Question(
        text = this.text,
        answer = answers.map { it.toAnswer() },
        ideas = ideas.map { it.toCommonIdea() }
    )
}

fun PartTwoResponse.Answer.toAnswer(): CommonTopicResponse.Answer {
    return CommonTopicResponse.Answer(text = this.text)
}

fun PartTwoResponse.Vocabulary.toVocabulary(): CommonTopicResponse.Vocabulary {
    return CommonTopicResponse.Vocabulary(text = this.text)
}

fun PartTwoResponse.Idea.toCommonIdea(): CommonTopicResponse.Idea {
    // Convert list of PartTwoBody to a single text string
    val combinedText = this.body.joinToString("\n") { it.text }
    return CommonTopicResponse.Idea(text = combinedText)
}

