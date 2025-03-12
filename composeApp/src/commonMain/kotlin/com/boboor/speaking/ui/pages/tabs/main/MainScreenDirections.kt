package com.boboor.speaking.ui.pages.tabs.main

import AppNavigator
import com.boboor.speaking.ui.pages.screens.topic.TopicScreen
import com.boboor.speaking.utils.Section


/*
    Created by Boburjon Murodov 20/12/24 at 20:00
*/

class MainScreenDirections(
    private val appNavigator: AppNavigator
) : MainScreenContracts.Directions {
    override suspend fun goTopicScreen(section: Section) {
        appNavigator.push(TopicScreen(section))
    }
}