package com.boboor.speaking.data.repository

import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 21/04/25 at 17:59
*/

interface TopicRepository {
    suspend fun syncAllTopics(): Job
    suspend fun updateProgress(topicId: String, isPartTwo: Boolean, newProgress: Int)
    suspend fun toggleBookmark(topicId: String, isPartTwo: Boolean)
    suspend fun markQuestionAnswered(questionId: String, isPartTwo: Boolean)
}