package com.boboor.speaking.ui.pages.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.ui.components.AppBar
import com.boboor.speaking.ui.components.DuoLingoCard
import com.boboor.speaking.ui.components.ScalableHorizontalPager
import com.boboor.speaking.ui.theme.DuoTypography
import com.boboor.speaking.ui.theme.DuolingoTheme
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_back_new
import io.ktor.http.ContentType
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import nl.marc_apps.tts.TextToSpeechEngine
import nl.marc_apps.tts.experimental.ExperimentalDesktopTarget
import nl.marc_apps.tts.experimental.ExperimentalIOSTarget
import nl.marc_apps.tts.rememberTextToSpeechOrNull
import org.jetbrains.compose.resources.painterResource
import kotlin.math.abs


/*
    Created by Boburjon Murodov 04/03/25 at 15:04
*/

@Serializable
data class DetailScreen(
    private val title: String,
    private val topics: List<CommonTopicResponse.Topic>,
    private val topicIndex: Int,
    private val questionIndex: Int,
) : Screen {
    override val key: ScreenKey
        get() = hashCode().toString()


    @ExperimentalDesktopTarget
    @Composable
    override fun Content() {
        val pagerState = rememberPagerState(
            initialPage = questionIndex,
            pageCount = { topics[topicIndex].questions.size }
        )

        ScalableHorizontalPager(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLow),
            pagerState = pagerState,
            beyondViewportPageCount = 1
        ) { page ->
            DetailScreenContent(
                questions = topics[topicIndex].questions[page],
                vocabularies = topics[topicIndex].vocabulary
            )
        }
    }


    @ExperimentalDesktopTarget
    @Composable
    private fun DetailScreenContent(
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
            containerColor = DuolingoTheme.colors.background,
            topBar = {
                Column {
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
                                navigator.pop()
                            }
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_back_new),
                                contentDescription = null,
                                tint = DuolingoTheme.colors.textColor
                            )
                        }

                        Text(
                            text = "Details",
                            style = DuolingoTheme.typography.heading.copy(fontWeight = FontWeight.Bold),
                            color = DuolingoTheme.colors.textColor
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .shadow(
                                5.dp,
                                spotColor = Color.Black,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(DuolingoTheme.colors.background)
                            .padding(16.dp)
                    ) {
                        list.forEach {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it, modifier = Modifier.weight(1f),
                                    style = DuolingoTheme.typography.bodyLarge.copy(
                                        color = DuolingoTheme.colors.textColor
                                    )
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                IconButton(onClick = {
                                    coroutine.launch {
                                        try {
                                            textToSpeech?.stop()
                                            textToSpeech?.say(it)
                                        } catch (e: Exception) {

                                        }
                                    }
                                }) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                                }
                            }
//                            HorizontalDivider()
                        }
                    }
                }

            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding() + 16.dp)
            ) {
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
                HorizontalPager(
                    pagerState,
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                ) { index ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                top = 16.dp,
                                bottom = innerPadding.calculateBottomPadding() + 16.dp,
                                start = 12.dp,
                                end = 12.dp
                            ),

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

                            when (index) {
                                0 -> {
                                    items(vocabularies) {
                                        Text(
                                            it.text,
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                    }
                                }

                                1 -> {
                                    item {
                                        FlowRow(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            questions.ideas.map { parseTaggedText(it.text) }
                                                .forEach {
                                                    when (it.type) {
                                                        TagType.ARROW -> {
                                                            Box(
                                                                modifier = Modifier
                                                                    .clip(RoundedCornerShape(12.dp))
                                                                    .background(
                                                                        DuolingoTheme.colors.duoBlue.copy(
                                                                            alpha = 0.1f
                                                                        )
                                                                    )
                                                                    .padding(
                                                                        horizontal = 8.dp,
                                                                        vertical = 4.dp
                                                                    )
                                                            ) {
                                                                Text(
                                                                    text = it.text,
                                                                    style = DuolingoTheme.typography.body.copy(
                                                                        fontWeight = FontWeight.SemiBold,
                                                                        color = DuolingoTheme.colors.duoBlue
                                                                    )
                                                                )
                                                            }
                                                        }

                                                        else -> {
                                                            Text(
                                                                modifier = Modifier.fillMaxWidth(),
                                                                text = it.text,
                                                                style = DuolingoTheme.typography.bodyLarge.copy(
                                                                    fontWeight = FontWeight.SemiBold,
                                                                    color = DuolingoTheme.colors.textColor
                                                                )
                                                            )
                                                        }

                                                    }

                                                }
                                        }

                                    }
                                }

                                else -> {
                                    items(questions.answer) {

                                        Text(
                                            it.text,
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }

        }

    }
}


data class ParsedItem(
    val text: String,
    val type: TagType
)

enum class TagType {
    ARROW, UNDERLINE, PLAIN, CATEGORY
}


fun parseTaggedText(raw: String): ParsedItem {
    val trimmed = raw.trim()

    return when {
        trimmed.contains("CATEGORY ") -> ParsedItem(
            text = trimmed.removePrefix("CATEGORY ")
                .replace(":", "")
                .replace("<u>", "")
                .replace("</u>", "")
                .trim(),
            type = TagType.CATEGORY
        )

        trimmed.startsWith("<arrow>") -> ParsedItem(
            text = trimmed.removePrefix("<arrow>").removeSuffix("</arrow>").trim(),
            type = TagType.ARROW
        )

        trimmed.startsWith("<u>") -> ParsedItem(
            text = trimmed.removePrefix("<u>").removeSuffix("</u>").trim(),
            type = TagType.UNDERLINE
        )

        else -> ParsedItem(trimmed, TagType.PLAIN)
    }
}