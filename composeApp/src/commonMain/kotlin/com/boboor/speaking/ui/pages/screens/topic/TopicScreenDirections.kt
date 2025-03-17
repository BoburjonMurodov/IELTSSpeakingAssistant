package com.boboor.speaking.ui.pages.screens.topic

import AppNavigator
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.ui.pages.screens.commonQuestions.CommonQuestionsScreen
import com.boboor.speaking.ui.pages.screens.detail.DetailScreen


/*
    Created by Boburjon Murodov 20/12/24 at 22:30
*/

class TopicScreenDirections(private val navigator: AppNavigator) : TopicScreenContracts.Directions {
    override suspend fun goQuestionsScreen(title: String, topics: List<CommonTopicResponse.Topic>, topicIndex: Int) {
        navigator.push(CommonQuestionsScreen(title = title, topics = topics, index = topicIndex));
    }

    override suspend fun goToDetailsScreen(topics: List<CommonTopicResponse.Topic>, topicIndex: Int) {
        navigator.push(
            DetailScreen(
                topics = topics,
                topicIndex = topicIndex,
                title = "",
                questionIndex = topicIndex
            )
        )
    }

    override suspend fun goBack() {
        navigator.back();
    }
}