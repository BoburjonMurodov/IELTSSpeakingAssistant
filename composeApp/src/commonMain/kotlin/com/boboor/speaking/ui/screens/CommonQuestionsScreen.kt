package com.boboor.speaking.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import cafe.adriel.voyager.core.screen.Screen
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.ui.components.AppBar


/*
    Created by Boburjon Murodov 25/02/25 at 01:28
*/

data class CommonQuestionsScreen(
    @Stable
    private val questions: List<CommonTopicResponse.Question>,
    private val title: String
) : Screen {

    @Composable
    override fun Content() {
        CommonQuestionsScreenContent(title, questions)
    }
}


@Composable
private fun CommonQuestionsScreenContent(
    title: String,
    questions: List<CommonTopicResponse.Question>
) {
    Scaffold(
        topBar = {
            AppBar(title = title, showSearch = false, onClickSearch = {}, onClickBack = {})
        }
    ) {
        LazyColumn(
            contentPadding = it
        ) {
            items(questions.size) {
                Text(text = questions[it].text)
            }
        }
    }
}