package com.boboor.speaking.data.models

import com.boboor.speaking.utils.enums.Section


/*
    Created by Boburjon Murodov 20/02/25 at 14:30
*/


data class TopicUIModel(
    val name: String,
    val free: Boolean,
    val new: Boolean,
    val order: Int,
    val questions: List<String>,
    val answers: List<String>,
    val ideas: List<String>,
    val vocabulary: List<String>,
    val type: Section,
    val uniqueContent: Map<String, String> = emptyMap()
)

