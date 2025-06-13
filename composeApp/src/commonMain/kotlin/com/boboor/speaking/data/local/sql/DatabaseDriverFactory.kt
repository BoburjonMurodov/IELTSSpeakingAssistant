package com.boboor.speaking.data.local.sql

import app.cash.sqldelight.db.SqlDriver
import com.boboor.AppDatabase
import com.boboor.speaking.data.local.sql.entities.CommonQuestion
import com.boboor.speaking.data.local.sql.entities.CommonTopic
import com.boboor.speaking.data.local.sql.entities.CommonTopicWithContent
import com.boboor.speaking.data.local.sql.entities.CommonVocabulary
import com.boboor.speaking.data.local.sql.entities.PartTwoQuestion
import com.boboor.speaking.data.local.sql.entities.PartTwoTopic
import com.boboor.speaking.data.local.sql.entities.PartTwoTopicWithContent
import com.boboor.speaking.data.local.sql.entities.PartTwoVocabulary
import com.boboor.CommonQuestion as SqlCommonQuestion
import com.boboor.CommonTopic as SqlCommonTopic
import com.boboor.CommonVocabulary as SqlCommonVocabulary
import com.boboor.PartTwoQuestion as SqlPartTwoQuestion
import com.boboor.PartTwoTopic as SqlPartTwoTopic
import com.boboor.PartTwoVocabulary as SqlPartTwoVocabulary

/**
 * Interface for platform-specific database driver creation
 */
interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

/**
 * Main database access class that wraps the SQLDelight generated database
 */
class LocalDatabase(
    databaseDriverFactory: DatabaseDriverFactory
) {
    private val database = AppDatabase(
        databaseDriverFactory.createDriver()
    )

    // Expose the query interfaces
    private val commonTopicQueries = database.appDatabaseQueries
    private val commonQuestionQueries = database.appDatabaseQueries
    private val commonVocabularyQueries = database.appDatabaseQueries

    private val partTwoTopicQueries = database.appDatabaseQueries
    private val partTwoQuestionQueries = database.appDatabaseQueries
    private val partTwoVocabularyQueries = database.appDatabaseQueries

    /**
     * Mapping functions for Common topics
     */
    private fun SqlCommonTopic.toDomain(): CommonTopic {
        return CommonTopic(
            name = name,
            active = active,
            free = free,
            order = order.toInt(),
            isBookmarked = isBookmarked,
            progress = progress.toInt()
        )
    }

    private fun SqlCommonQuestion.toDomain(): CommonQuestion {
        return CommonQuestion(
            id = id,
            topicId = topicId,
            text = text,
            isAnswered = isAnswered,
            isMastered = isMastered
        )
    }

    private fun SqlCommonVocabulary.toDomain(): CommonVocabulary {
        return CommonVocabulary(
            topicId = topicId,
            text = text
        )
    }

    /**
     * Mapping functions for PartTwo topics
     */
    private fun SqlPartTwoTopic.toDomain(): PartTwoTopic {
        return PartTwoTopic(
            name = name,
            active = active,
            free = free,
            order = order.toInt(),
            isBookmarked = isBookmarked,
            progress = progress.toInt()
        )
    }

    private fun SqlPartTwoQuestion.toDomain(): PartTwoQuestion {
        return PartTwoQuestion(
            topicId = topicId,
            text = text,
            isAnswered = isAnswered,
            isMastered = isMastered
        )
    }

    private fun SqlPartTwoVocabulary.toDomain(): PartTwoVocabulary {
        return PartTwoVocabulary(
            topicId = topicId,
            text = text
        )
    }

    /**
     * Common Topic operations
     */
    fun getAllCommonTopics(): List<CommonTopic> {
        return commonTopicQueries.getAllTopicsWithContent().executeAsList().map {  it.toDomain() }
    }

    fun getCommonTopicWithContent(id: String): CommonTopicWithContent? {
        val topic = commonTopicQueries.getTopicWithContent(id).executeAsOneOrNull()?.toDomain() ?: return null
        val vocabulary = getVocabularyForCommonTopic(id)

        return CommonTopicWithContent(
            vocabulary = vocabulary
        )
    }

    fun getQuestionsForCommonTopic(topicId: String): List<CommonQuestion> {
        return commonQuestionQueries.getQuestionsForTopic(topicId).executeAsList().map { it.toDomain() }
    }

    fun getVocabularyForCommonTopic(topicId: String): List<CommonVocabulary> {
        return commonVocabularyQueries.getVocabularyForTopic(topicId).executeAsList().map { it.toDomain() }
    }

    fun insertCommonTopic(topic: CommonTopic, id: String) {
        commonTopicQueries.insertTopic(
            id = id,
            name = topic.name,
            active = topic.active,
            free = topic.free,
            order = topic.order.toLong(),
            isBookmarked = topic.isBookmarked,
            progress = topic.progress.toLong()
        )
    }

    fun insertCommonQuestion(question: CommonQuestion, id: String) {
        commonTopicQueries.insertQuestion(
            id = id,
            topicId = question.topicId,
            text = question.text,
            isAnswered = question.isAnswered,
            isMastered = question.isMastered
        )
    }

    fun insertCommonVocabulary(vocabulary: CommonVocabulary, id: String) {
        commonTopicQueries.insertVocabulary(
            id = id,
            topicId = vocabulary.topicId,
            text = vocabulary.text
        )
    }

    fun updateCommonTopic(topic: CommonTopic, id: String) {
        commonTopicQueries.updateTopic(
            name = topic.name,
            active = topic.active,
            free = topic.free,
            order = topic.order.toLong(),
            isBookmarked = topic.isBookmarked,
            progress = topic.progress.toLong(),
            id = id
        )
    }

    fun updateCommonQuestion(question: CommonQuestion) {
        commonTopicQueries.updateQuestion(
            text = question.text,
            isAnswered = question.isAnswered,
            isMastered = question.isMastered,
            id = question.id
        )
    }

    fun clearCommonTopics() {
        commonTopicQueries.clearTopics()
    }

    /**
     * PartTwo Topic operations
     */
    fun getAllPartTwoTopics(): List<PartTwoTopic> {
        return partTwoTopicQueries.getAllPartTwoTopicsWithContent().executeAsList().map { it.toDomain() }
    }

    fun getPartTwoTopicWithContent(id: String): PartTwoTopicWithContent? {
        val topic = partTwoTopicQueries.getPartTwoTopicWithContent(id).executeAsOneOrNull()?.toDomain() ?: return null
        val vocabulary = getVocabularyForPartTwoTopic(id)

        return PartTwoTopicWithContent(
            vocabulary = vocabulary
        )
    }

    fun getQuestionsForPartTwoTopic(topicId: String): List<PartTwoQuestion> {
        return partTwoQuestionQueries.getPartTwoQuestionsForTopic(topicId).executeAsList().map { it.toDomain() }
    }

    fun getVocabularyForPartTwoTopic(topicId: String): List<PartTwoVocabulary> {
        return partTwoVocabularyQueries.getPartTwoVocabularyForTopic(topicId).executeAsList().map { it.toDomain() }
    }

    fun insertPartTwoTopic(topic: PartTwoTopic, id: String) {
        partTwoTopicQueries.insertPartTwoTopic(
            id = id,
            name = topic.name,
            active = topic.active,
            free = topic.free,
            order = topic.order.toLong(),
            isBookmarked = topic.isBookmarked,
            progress = topic.progress.toLong()
        )
    }

    fun insertPartTwoQuestion(question: PartTwoQuestion, id: String) {
        partTwoTopicQueries.insertPartTwoQuestion(
            id = id,
            topicId = question.topicId,
            text = question.text,
            isAnswered = question.isAnswered,
            isMastered = question.isMastered
        )
    }

    fun insertPartTwoVocabulary(vocabulary: PartTwoVocabulary, id: String) {
        partTwoTopicQueries.insertPartTwoVocabulary(
            id = id,
            topicId = vocabulary.topicId,
            text = vocabulary.text
        )
    }

    fun updatePartTwoTopic(topic: PartTwoTopic, id: String) {
        partTwoTopicQueries.updatePartTwoTopic(
            name = topic.name,
            active = topic.active,
            free = topic.free,
            order = topic.order.toLong(),
            isBookmarked = topic.isBookmarked,
            progress = topic.progress.toLong(),
            id = id
        )
    }

    fun updatePartTwoQuestion(question: PartTwoQuestion, id: String) {
        partTwoTopicQueries.updatePartTwoQuestion(
            text = question.text,
            isAnswered = question.isAnswered,
            isMastered = question.isMastered,
            id = id
        )
    }

    /**
     * Transaction helpers
     */
    fun insertCommonTopicWithContent(
        topic: CommonTopic,
        questions: List<CommonQuestion>,
        vocabulary: List<CommonVocabulary>,
        topicId: String
    ) {
        database.transaction {
            insertCommonTopic(topic, topicId)

            questions.forEach { question ->
                insertCommonQuestion(question, question.id)
            }

            vocabulary.forEach { vocab ->
                insertCommonVocabulary(vocab, vocab.topicId)
            }
        }
    }

    fun insertPartTwoTopicWithContent(
        topic: PartTwoTopic,
        questions: List<PartTwoQuestion>,
        vocabulary: List<PartTwoVocabulary>,
        topicId: String
    ) {
        database.transaction {
            insertPartTwoTopic(topic, topicId)

            questions.forEach { question ->
                insertPartTwoQuestion(question, question.topicId)
            }

            vocabulary.forEach { vocab ->
                insertPartTwoVocabulary(vocab, vocab.topicId)
            }
        }
    }

    /**
     * Helper to generate unique IDs for new entries
     */

    /**
     * Closes the database connection
     */

}