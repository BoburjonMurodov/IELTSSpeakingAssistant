package com.boboor.speaking.data.local.room.entities


/*
    Created by Boburjon Murodov 21/04/25 at 17:35
*/

data class PartTwoTopicWithContent(
//    @Embedded val topic: PartTwoTopic,
//
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "topicId"
//    )
//    val questions: List<PartTwoQuestion>,
//
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "topicId"
//    )
    val vocabulary: List<PartTwoVocabulary>
)


//@Entity(tableName = "part_two_topic")
data class PartTwoTopic(
//    @PrimaryKey val id: String,
    val name: String,
    val active: Boolean,
    val free: Boolean,
    val order: Int,
    val isBookmarked: Boolean = false,
    val progress: Int = 0
)

//@Entity(tableName = "part_two_question")
data class PartTwoQuestion(
//    @PrimaryKey val id: String,
    val topicId: String,
    val text: String,
    val isAnswered: Boolean = false
)

//@Entity(tableName = "part_two_vocabulary")
data class PartTwoVocabulary(
//    @PrimaryKey val id: String,
    val topicId: String,
    val text: String
)