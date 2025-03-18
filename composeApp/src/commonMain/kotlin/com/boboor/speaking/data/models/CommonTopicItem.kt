package com.boboor.speaking.data.models

import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse
import kotlinx.serialization.Serializable


/*
    Created by Boburjon Murodov 17/03/25 at 16:46
*/

@Serializable
data class CommonTopicItem(
    val question: String,
    val new: Boolean,
    val order: Int,
    val active: Boolean,
    val size: Int = -1
)

fun PartTwoResponse.Topic.toCommonTopicItem(order: Int = 0): CommonTopicItem =
    CommonTopicItem(
        question = name,
        new = new == "new",
        order = order,
        active = active,
        size = questions.size
    )

fun CommonTopicResponse.Topic.toCommonTopicItem(order: Int = 0): CommonTopicItem =
    CommonTopicItem(
        question = name,
        new = new == "new",
        order = order,
        active = active,
        size = questions.size
    )