package com.boboor.speaking.ui.screens.common_questions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.ui.components.AppBar
import com.boboor.speaking.ui.screens.detail_screen.DetailScreen
import com.boboor.speaking.utils.debounceClickable
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource

data class CommonQuestionsScreen(
    @Stable
    private val questions: List<CommonTopicResponse.Question>,
    private val title: String
) : Screen {

    override val key: ScreenKey
        get() = hashCode().toString()

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
    val navigator = LocalNavigator.current
    val hazeState = remember { HazeState() }
    val list = remember { mutableStateListOf<List<String>>() }
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()


    LaunchedEffect(Unit) {
        list.clear()
        val builder = StringBuilder()

        println("questions size: ${questions.size}")
        questions.forEach {
            builder.clear()
            val tempList = mutableListOf<String>()
            builder.append(it.text)
            val separatedQuestions = builder.toString()
                .split("#")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            tempList.addAll(separatedQuestions)
            list.add(tempList)
        }
    }

    Scaffold(
        topBar = {
            AppBar(modifier = Modifier.hazeEffect(
                hazeState,
                style = HazeDefaults.style(
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    blurRadius = 25.dp
                )
            ), title = title, showSearch = false, onClickSearch = {}, onClickBack = {
                navigator?.pop()
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier.hazeSource(state = hazeState),
            contentPadding = PaddingValues(
                top = it.calculateTopPadding() + 16.dp,
                bottom = navigationBarHeight + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(list.size) {
                if (list[it].size == 1) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                            .debounceClickable { navigator?.push(DetailScreen("", questions[it])) }
                            .padding(16.dp)
                    ) {
                        Text(text = list[it].first())
                    }
                } else {
                    list[it].forEachIndexed() { index, question ->
                        if (index != 0 && index != list[it].size) {
                            Spacer(Modifier.height(4.dp))
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .then(
                                    if (index == 0) {
                                        Modifier.clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                                    } else if (index == list[it].size - 1) {
                                        Modifier.clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                                    } else {
                                        Modifier.clip(RoundedCornerShape(0.dp))
                                    }
                                )
                                .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                                .debounceClickable { navigator?.push(DetailScreen("", questions[it])) }
                                .padding(16.dp)
                        ) {
                            Text(text = question)
                        }
                    }

                }
            }
        }
    }
}



