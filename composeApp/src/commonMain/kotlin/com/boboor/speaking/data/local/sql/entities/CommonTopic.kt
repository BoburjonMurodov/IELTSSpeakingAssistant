package com.boboor.speaking.data.local.sql.entities

import kotlinx.serialization.Serializable


/*
    Created by Boburjon Murodov 21/04/25 at 17:34
*/

data class CommonTopicWithContent(
//    @Embedded val topic: CommonTopic,

//    @Relation(
//        parentColumn = "id",
//        entityColumn = "topicId"
//    )
//    val questions: List<CommonQuestion>,
//
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "topicId"
//    )
    val vocabulary: List<CommonVocabulary>
)

//@Entity(tableName = "common_topic")
@Serializable
data class CommonTopic(
//    @PrimaryKey val id: String,
    val name: String,
    val active: Boolean,
    val free: Boolean,
    val order: Int,
    val isBookmarked: Boolean = false,
    val progress: Int = 0
)

//@Entity(tableName = "common_question")
@Serializable
data class CommonQuestion(
//    @PrimaryKey
    val id: String,
    val topicId: String,
    val text: String,
    val isAnswered: Boolean = false,
    val isMastered: Boolean = false
)

//@Entity(tableName = "common_vocabulary")
@Serializable
data class CommonVocabulary(
//    @PrimaryKey val id: String,
    val topicId: String,
    val text: String
)

