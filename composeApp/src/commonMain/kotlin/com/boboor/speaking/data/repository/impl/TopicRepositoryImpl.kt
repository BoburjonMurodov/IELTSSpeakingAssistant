package com.boboor.speaking.data.repository.impl

import com.boboor.speaking.data.local.room.dao.CommonTopicDao
import com.boboor.speaking.data.local.room.dao.PartTwoTopicDao
import com.boboor.speaking.data.local.room.entities.CommonQuestion
import com.boboor.speaking.data.local.room.entities.CommonTopic
import com.boboor.speaking.data.local.room.entities.CommonVocabulary
import com.boboor.speaking.data.local.room.entities.PartTwoQuestion
import com.boboor.speaking.data.local.room.entities.PartTwoTopic
import com.boboor.speaking.data.local.room.entities.PartTwoVocabulary
import com.boboor.speaking.data.remote.ApiService
import com.boboor.speaking.data.repository.TopicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/*
    Created by Boburjon Murodov 21/04/25 at 18:00
*/

//
//class TopicRepositoryImpl(
//    private val commonDao: CommonTopicDao,
//    private val partTwoDao: PartTwoTopicDao,
//    private val api: ApiService
//) : TopicRepository {
//
//    override suspend fun syncAllTopics() = withContext(Dispatchers.IO) {
//        launch { syncCommon() }
//        launch { syncPartTwo() }
//    }
//
//    private suspend fun syncCommon() = withContext(Dispatchers.IO) {
//        val partOne = async { api.getPartOneQuestions() }
//        val partThree = async { api.getPartThreeQuestions() }
//        val mergedContent = partOne.await().content + partThree.await().content
//
//        mergedContent.forEach { (topicId, topic) ->
//            val existing = commonDao.getTopicWithContent(topicId)
//
//            val dbTopic = CommonTopic(
//                id = topicId,
//                name = topic.name,
//                active = topic.active,
//                free = topic.free,
//                order = topic.order,
//                isBookmarked = existing?.topic?.isBookmarked ?: false,
//                progress = existing?.topic?.progress ?: 0
//            )
//
//            val dbQuestions = topic.questions.mapIndexed { index, question ->
//                CommonQuestion(
//                    id = "$topicId-q$index",
//                    topicId = topicId,
//                    text = question.text,
//                    isAnswered = existing?.questions
//                        ?.find { it.text == question.text }?.isAnswered ?: false
//                )
//            }
//
//            val dbVocab = topic.vocabulary.mapIndexed { index, vocab ->
//                CommonVocabulary(
//                    id = "$topicId-v$index",
//                    topicId = topicId,
//                    text = vocab.text
//                )
//            }
//
//            commonDao.insertTopics(listOf(dbTopic))
//            commonDao.insertQuestions(dbQuestions)
//            commonDao.insertVocabulary(dbVocab)
//        }
//    }
//
//    private suspend fun syncPartTwo() {
//        val response = api.getPartTwoQuestions()
//
//        response.content.forEach { (topicId, topic) ->
//            val existing = partTwoDao.getTopicWithContent(topicId)
//
//            val dbTopic = PartTwoTopic(
//                id = topicId,
//                name = topic.name,
//                active = topic.active,
//                free = topic.free,
//                order = topic.order,
//                isBookmarked = existing?.topic?.isBookmarked ?: false,
//                progress = existing?.topic?.progress ?: 0
//            )
//
//            val dbQuestions = topic.questions.mapIndexed { index, q ->
//                PartTwoQuestion(
//                    id = "$topicId-q$index",
//                    topicId = topicId,
//                    text = q.text,
//                    isAnswered = existing?.questions
//                        ?.find { it.text == q.text }?.isAnswered ?: false
//                )
//            }
//
//            val dbVocab = topic.vocabulary.mapIndexed { index, v ->
//                PartTwoVocabulary(
//                    id = "$topicId-v$index",
//                    topicId = topicId,
//                    text = v.text
//                )
//            }
//
//            partTwoDao.insertTopics(listOf(dbTopic))
//            partTwoDao.insertQuestions(dbQuestions)
//            partTwoDao.insertVocabulary(dbVocab)
//        }
//    }
//
//    override suspend fun updateProgress(topicId: String, isPartTwo: Boolean, newProgress: Int) {
//        if (isPartTwo) {
//            partTwoDao.getTopicWithContent(topicId)?.topic?.let {
//                partTwoDao.updateTopic(it.copy(progress = newProgress))
//            }
//        } else {
//            commonDao.getTopicWithContent(topicId)?.topic?.let {
//                commonDao.updateTopic(it.copy(progress = newProgress))
//            }
//        }
//    }
//
//    override suspend fun toggleBookmark(topicId: String, isPartTwo: Boolean) {
//        if (isPartTwo) {
//            partTwoDao.getTopicWithContent(topicId)?.topic?.let {
//                partTwoDao.updateTopic(it.copy(isBookmarked = !it.isBookmarked))
//            }
//        } else {
//            commonDao.getTopicWithContent(topicId)?.topic?.let {
//                commonDao.updateTopic(it.copy(isBookmarked = !it.isBookmarked))
//            }
//        }
//    }
//
//    override suspend fun markQuestionAnswered(questionId: String, isPartTwo: Boolean) {
//        if (isPartTwo) {
//            val question = partTwoDao.getAllTopicsWithContent()
//                .flatMap { it.questions }
//                .find { it.id == questionId } ?: return
//            partTwoDao.updateQuestion(question.copy(isAnswered = true))
//        } else {
//            val question = commonDao.getAllTopicsWithContent()
//                .flatMap { it.questions }
//                .find { it.id == questionId } ?: return
//            commonDao.updateQuestion(question.copy(isAnswered = true))
//        }
//    }
//}