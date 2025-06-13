package com.boboor.speaking.ui.pages.screens.commonQuestions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.ui.components.AppBar
import com.boboor.speaking.ui.components.SwipeToDismissPage
import com.boboor.speaking.ui.pages.screens.detail.DetailScreen
import com.boboor.speaking.ui.theme.DuolingoTheme
import com.boboor.speaking.utils.debounceClickable
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_back_new
import org.jetbrains.compose.resources.painterResource

data class CommonQuestionsScreen(
    private val title: String,
    private val topics: List<CommonTopicResponse.Topic>,
    private val index: Int
) : Screen {

    override val key: ScreenKey
        get() = hashCode().toString()

    @Composable
    override fun Content() {
        SwipeToDismissPage { CommonQuestionsScreenContent(title, topics, index) }
    }
}

@Composable
private fun CommonQuestionsScreenContent(
    title: String,
    topics: List<CommonTopicResponse.Topic>,
    topicIndex: Int
) {
    val navigator = LocalNavigator.current
    val hazeState = remember { HazeState() }
    val list = remember { mutableStateListOf<List<String>>() }
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()


    LaunchedEffect(Unit) {
        list.clear()
        val builder = StringBuilder()

        println("questions size: ${topics[topicIndex].questions.size}")
        topics[topicIndex].questions.forEach {
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
        containerColor = DuolingoTheme.colors.secondaryBackground,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .shadow(10.dp, spotColor = Color.Gray)
                    .background(DuolingoTheme.colors.background)
                    .statusBarsPadding()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = {
                        navigator?.pop()
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back_new),
                        contentDescription = null,
                        tint = DuolingoTheme.colors.textColor
                    )
                }

                Text(
                    text = title,
                    style = DuolingoTheme.typography.heading.copy(fontWeight = FontWeight.Bold),
                    color = DuolingoTheme.colors.textColor
                )
            }
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

            items(list.size) { questionIndex ->
                if (list[questionIndex].size == 1) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(DuolingoTheme.colors.background)
                            .debounceClickable {
                                navigator?.push(
                                    DetailScreen(
                                        "",
                                        topics = topics,
                                        topicIndex = topicIndex,
                                        questionIndex = questionIndex
                                    )
                                )
                            }
                            .padding(16.dp)
                    ) {
                        Text(
                            text = list[questionIndex].first(),
                            style = DuolingoTheme.typography.bodyLarge.copy(
                                color = DuolingoTheme.colors.textColor
                            )
                        )
                    }
                } else {
                    list[questionIndex].forEachIndexed { index, question ->
                        if (index != 0 && index != list[questionIndex].size) {
                            Spacer(Modifier.height(4.dp))
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .then(
                                    if (index == 0) {
                                        Modifier.clip(
                                            RoundedCornerShape(
                                                topEnd = 16.dp,
                                                topStart = 16.dp,
                                                bottomEnd = 4.dp,
                                                bottomStart = 4.dp
                                            )
                                        )
                                    } else if (index == list[questionIndex].size - 1) {
                                        Modifier.clip(
                                            RoundedCornerShape(
                                                bottomEnd = 16.dp,
                                                bottomStart = 16.dp,
                                                topEnd = 4.dp,
                                                topStart = 4.dp,
                                            )
                                        )
                                    } else {
                                        Modifier.clip(RoundedCornerShape(4.dp))
                                    }
                                )
                                .background(DuolingoTheme.colors.background)
                                .debounceClickable {
                                    navigator?.push(
                                        DetailScreen(
                                            "",
                                            topics = topics,
                                            topicIndex = topicIndex,
                                            questionIndex = questionIndex
                                        )
                                    )
                                }
                                .padding(16.dp)
                        ) {
                            Text(
                                text = question,
                                style = DuolingoTheme.typography.bodyLarge.copy(
                                    color = DuolingoTheme.colors.textColor
                                )
                            )
                        }
                    }

                }
            }
        }
    }
}



