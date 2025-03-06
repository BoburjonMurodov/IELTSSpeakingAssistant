@file:OptIn(ExperimentalFoundationApi::class)

package com.boboor.speaking.ui.screens.topic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.boboor.speaking.presenter.topic.TopicScreenContracts
import com.boboor.speaking.ui.components.AppBar
import com.boboor.speaking.ui.components.SearchInput
import com.boboor.speaking.ui.components.TopicItem
import com.boboor.speaking.ui.components.TopicItemShimmer
import com.boboor.speaking.utils.Section
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource


/*
    Created by Boburjon Murodov 20/12/24 at 18:14
*/

class TopicScreen(private val section: Section) : Screen {
    override val key: ScreenKey get() = this.hashCode().toString()


    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<TopicScreenContracts.ViewModel>()
        LifecycleEffectOnce { viewModel.init(section = section) }
        val state = viewModel.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
//        SwipeToDismissScreen(onDismiss = { navigator.pop() }) {
            TopicScreenContent(state, viewModel.searchQuery, viewModel::onEventDispatcher)
//        }
    }
}


@Composable
private fun TopicScreenContent(
    state: State<TopicScreenContracts.UIState>,
    searchQuery: MutableState<String>,
    onEventDispatcher: (TopicScreenContracts.Intent) -> Unit
) {
    val isSearchVisible = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val snackBarHostState = remember { SnackbarHostState() }
    val hazeState = remember { HazeState() }
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(snackBarHostState,
                snackbar = {
                    Box(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 16.dp, vertical = 12.dp), contentAlignment = Alignment.CenterStart
                    ) {

                        Text(
                            it.visuals.message,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                }
            )
        },
        topBar = {
            AppBar(
                title = state.value.section.title,
                modifier = Modifier.hazeEffect(
                    hazeState,
                    style = HazeDefaults.style(
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        blurRadius = 25.dp
                    )
                    ),
                isSearchVisible = !state.value.isLoading,
                onClickBack = { onEventDispatcher.invoke(TopicScreenContracts.Intent.OnClickBack) },
                onClickSearch = {
                    isSearchVisible.value = !isSearchVisible.value
                }
            )
        },
    ) {

        LazyColumn(
            userScrollEnabled = !state.value.isLoading,
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp + navigationBarHeight, top = it.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            stickyHeader {
                Column {
                    AnimatedVisibility(isSearchVisible.value) {
                        Column {
                            Spacer(Modifier.height(24.dp))
                            Box {
                                Column(
                                    modifier = Modifier.height(70.dp)
                                        .fillMaxWidth()
                                        .blur(25.dp)
                                ) {
                                }

                                SearchInput(searchQuery) {
                                    onEventDispatcher.invoke(TopicScreenContracts.Intent.SearchQuery)
                                }

                            }
                            Spacer(Modifier.height(12.dp))

                        }
                    }
                }
            }

            items(state.value.questions.size, key = { state.value.questions[it].name }) {
                val isExpanded = rememberSaveable { mutableStateOf(false) }
                val hasOverFlow = rememberSaveable { mutableStateOf(false) }
                TopicItem(state.value.questions[it], it + 1, isExpanded, hasOverFlow, searchQuery.value) {

                    onEventDispatcher.invoke(
                        TopicScreenContracts.Intent.OnClickTopic(
                            title = state.value.questions[it].name,
                            topics = state.value.questions,
                            topicIndex = it
                        )
                    )
                }
            }

            if (state.value.isLoading) {
                items(20) {
                    TopicItemShimmer()
                }
            }

            if (state.value.questions.isEmpty()) {
                item {
                    Box(
                        Modifier.fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No topics found")
                    }
                }
            }
        }
    }
}

@Composable
fun SwipeToDismissScreen(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "")

    Box(
        Modifier
            .fillMaxSize()
            .offset { IntOffset(animatedOffsetX.toInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX > 300f) { // Threshold for dismiss
                            onDismiss()
                        } else {
                            offsetX = 0f
                        }
                    }
                ) { change, dragAmount ->
                    offsetX += dragAmount
                }
            }
    ) {
        content()
    }
}

