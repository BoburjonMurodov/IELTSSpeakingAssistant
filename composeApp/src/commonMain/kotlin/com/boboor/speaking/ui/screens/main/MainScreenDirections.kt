package com.boboor.speaking.ui.screens.main

import AppNavigator
import androidx.compose.runtime.Composable
import com.boboor.speaking.presenter.main.MainScreenContracts
import com.boboor.speaking.ui.screens.topic.TopicScreen


/*
    Created by Boburjon Murodov 20/12/24 at 20:00
*/

class MainScreenDirections(
    private val appNavigator: AppNavigator
) : MainScreenContracts.Directions {
    override suspend fun goTopicScreen() {
        appNavigator.push(TopicScreen())
    }
}