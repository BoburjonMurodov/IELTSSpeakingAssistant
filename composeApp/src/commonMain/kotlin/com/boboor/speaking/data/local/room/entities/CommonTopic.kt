package com.boboor.speaking.data.local.room.entities


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
data class CommonQuestion(
//    @PrimaryKey
    val id: String,
    val topicId: String,
    val text: String,
    val isAnswered: Boolean = false
)

//@Entity(tableName = "common_vocabulary")
data class CommonVocabulary(
//    @PrimaryKey val id: String,
    val topicId: String,
    val text: String
)