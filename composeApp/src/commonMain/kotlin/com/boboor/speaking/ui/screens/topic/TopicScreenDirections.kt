package com.boboor.speaking.ui.screens.topic

import AppNavigator
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.presenter.topic.TopicScreenContracts
import com.boboor.speaking.ui.screens.CommonQuestionsScreen


/*
    Created by Boburjon Murodov 20/12/24 at 22:30
*/

class TopicScreenDirections(private val navigator: AppNavigator) : TopicScreenContracts.Directions {
    override suspend fun goQuestionsScreen(title: String, list: List<CommonTopicResponse.Question>) {
        navigator.push(CommonQuestionsScreen(title = title, questions = list));
    }

    override suspend fun goBack() {
        navigator.back();
    }
}