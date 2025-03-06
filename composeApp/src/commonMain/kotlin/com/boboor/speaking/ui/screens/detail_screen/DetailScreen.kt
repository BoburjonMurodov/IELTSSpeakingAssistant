package com.boboor.speaking.ui.screens.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.getScreenWidth
import com.boboor.speaking.ui.components.AppBar
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import kotlinx.coroutines.launch
import multiplatform.network.cmptoast.showToast
import nl.marc_apps.tts.TextToSpeechEngine
import nl.marc_apps.tts.experimental.ExperimentalDesktopTarget
import nl.marc_apps.tts.experimental.ExperimentalIOSTarget
import nl.marc_apps.tts.rememberTextToSpeechOrNull
import kotlin.math.abs


/*
    Created by Boburjon Murodov 04/03/25 at 15:04
*/

data class DetailScreen(
    private val title: String,
    private val topics: List<CommonTopicResponse.Topic>,
    private val topicIndex: Int,
    private val questionIndex: Int,
) : Screen {
    override val key: ScreenKey
        get() = hashCode().toString()


    @ExperimentalIOSTarget
    @ExperimentalDesktopTarget
    @Composable
    override fun Content() {

        val pagerState = rememberPagerState(
            initialPage = questionIndex,

            pageCount = { topics[topicIndex].questions.size }
        )

        HorizontalPager(state = pagerState,
            beyondViewportPageCount = 1
            ) { page ->
            val scrollProgress = abs(pagerState.currentPageOffsetFraction).coerceAtMost(1f)
            val cornerRadius by remember { derivedStateOf { lerp(32.dp, 0.dp, scrollProgress) } }


            val scale = when {
                scrollProgress <= 0.5f -> lerp(1f, 0.9f, scrollProgress * 2f)
                else -> 0.9f
            }


            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .fillMaxSize()
                    .clip(RoundedCornerShape(cornerRadius))
            ) {
                DetailScreenContent(
                    title = title,
                    questions = topics[topicIndex].questions[page],
                    vocabularies = topics[topicIndex].vocabulary
                )
            }
        }


    }
}


@ExperimentalIOSTarget
@ExperimentalDesktopTarget
@Composable
private fun DetailScreenContent(
    title: String,
    questions: CommonTopicResponse.Question,
    vocabularies: List<CommonTopicResponse.Vocabulary>
) {
    val navigator = LocalNavigator.currentOrThrow
    val list = remember { mutableStateListOf<String>() }
    val textToSpeech = rememberTextToSpeechOrNull(TextToSpeechEngine.Google)
    val coroutine = rememberCoroutineScope()
    val tabs = remember { mutableStateListOf("Vocabulary", "Ideas", "Answers") }
    val tabsOpen = remember { mutableStateListOf(false, false, false) }
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    LaunchedEffect(Unit) {
        list.clear()
        val builder = StringBuilder()
        builder.append(questions.text)
        val temp = builder.split("#")
        list.addAll(temp)
    }

    DisposableEffect(Unit) {
        onDispose {
            textToSpeech?.close()
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                onClickBack = { navigator.pop() },
                title = "Detail",
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHighest),
                showSearch = false,
                onClickSearch = { },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                list.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it, modifier = Modifier.weight(1f))

                        Spacer(modifier = Modifier.width(16.dp))

                        IconButton(onClick = {
                            coroutine.launch {
                                try {
                                    textToSpeech?.stop()
                                    textToSpeech?.say(it)
                                } catch (e: Exception) {
                                    showToast("error")
                                }
                            }
                        }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                        }
                    }
                    HorizontalDivider()
                }
            }
            Spacer(Modifier.height(16.dp))
            ScrollableTabRow(
                modifier = Modifier.fillMaxWidth(), selectedTabIndex = pagerState.currentPage
            ) {
                tabs.forEachIndexed { tabIndex, value ->
                    Tab(selected = pagerState.currentPage == tabIndex, onClick = {
                        coroutine.launch {
                            pagerState.animateScrollToPage(tabIndex)
                        }
                    }) {
                        Text(tabs[tabIndex])
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            HorizontalPager(
                pagerState,
                modifier = Modifier.weight(1f)
                    .fillMaxWidth()
            ) { index ->
                when (index) {
                    0 -> {
                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 16.dp),

                            modifier = Modifier
                                .fillMaxSize()
                                .background(if (tabsOpen[0]) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent)
                                .pointerInput(null) {
                                    detectTapGestures(
                                        onLongPress = {
                                            tabsOpen[index] = !tabsOpen[index]
                                        }
                                    )
                                },

                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(vocabularies) {
                                val richText = rememberRichTextState()
                                richText.setHtml(it.text.replace("<arrow>", "→ ")).annotatedString
                                Text(richText.annotatedString, modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }

                    1 -> {
                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 16.dp),

                            modifier = Modifier.weight(1f)
                                .background(if (tabsOpen[0]) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent)
                                .pointerInput(null) {
                                    detectTapGestures(
                                        onLongPress = {
                                            tabsOpen[index] = !tabsOpen[index]
                                        }
                                    )
                                },
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(questions.ideas) {
                                val richText = rememberRichTextState()
                                richText.setHtml(it.text.replace("<arrow>", "→ ")).annotatedString
                                Text(richText.annotatedString, modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }

                    else -> {
                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 16.dp),
                            modifier = Modifier.weight(1f)
                                .background(if (tabsOpen[0]) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent)
                                .pointerInput(null) {
                                    detectTapGestures(
                                        onLongPress = {
                                            tabsOpen[index] = !tabsOpen[index]
                                        }
                                    )
                                },
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(questions.answer) {
                                val richText = rememberRichTextState()
                                richText.setHtml(it.text.replace("<arrow>", "→ "))

                                Text(richText.annotatedString, modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                }
            }
        }

    }
}
























