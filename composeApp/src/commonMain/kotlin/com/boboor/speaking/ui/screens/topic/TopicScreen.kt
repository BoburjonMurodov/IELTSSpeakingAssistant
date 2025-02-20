package com.boboor.speaking.ui.screens.topic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.boboor.speaking.data.models.PartOneResponse
import com.boboor.speaking.presenter.topic.TopicScreenContracts
import com.boboor.speaking.ui.screens.main.appShadow
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource


/*
    Created by Boburjon Murodov 20/12/24 at 18:14
*/

class TopicScreen : Screen {
    override val key: ScreenKey get() =  this.hashCode().toString()


    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<TopicScreenContracts.ViewModel>()

        LifecycleEffectOnce{ viewModel.init() }

        val state = viewModel.container.stateFlow.collectAsState()

        TopicScreenContent(state, viewModel.searchQuery, viewModel::onEventDispatcher)
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TopicScreenContent(
    state: State<TopicScreenContracts.UIState>,
    searchQuery: MutableState<String>,
    onEventDispatcher: (TopicScreenContracts.Intent) -> Unit
) {
    val isSearchVisible = remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = Color(0xfff7fcfe),
        topBar = @Composable {
            Row(
                Modifier
                    .shadow(elevation = 10.dp)
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding(),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 15.dp,
                                spotColor = Color.Black.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White)
                            .clickable { onEventDispatcher.invoke(TopicScreenContracts.Intent.OnClickBack) }
                            .padding(6.dp),

                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = null,
                        )

                    }

                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Part 1",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )


                    IconButton(
                        onClick = {
                            isSearchVisible.value = !isSearchVisible.value
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            modifier = Modifier.size(24.dp),
                            contentDescription = null,
                        )
                    }

                }

            }
        }
    ) {

        if (state.value.isLoading) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(bottom = 36.dp),
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

                items(state.value.questions.size) {
                    val isExpanded = rememberSaveable { mutableStateOf(false) }
                    val hasOverFlow = rememberSaveable { mutableStateOf(false) }
                    TopicItem(state.value.questions[it], it + 1, isExpanded, hasOverFlow)
                }

            }
        }
    }
}


@Composable
fun SearchInput(
    query: MutableState<String>,
    onValueChange: () -> Unit
) {
    val focusRequest = FocusRequester()

    LaunchedEffect(Unit){
        focusRequest.requestFocus()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                BorderStroke(width = 2.dp, color = Color(0xff939393).copy(alpha = 0.2f)),
                shape = RoundedCornerShape(20.dp)
            )
            .background(Color.White)
            .clickable(interactionSource = null, indication = null) { focusRequest.requestFocus() }
            .padding(horizontal = 8.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            modifier = Modifier.padding(start = 12.dp),
            contentDescription = null,
            tint = Color.Gray
        )

        BasicTextField(
            modifier = Modifier.weight(1f)
                .focusRequester(focusRequest)
                .padding(start = 12.dp),
            value = query.value,
            onValueChange = {
                if (it.length < 20) {
                    query.value = it
                    onValueChange()
                }
            },
            maxLines = 1,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        ) {
            if (query.value.isEmpty()) {
                Text(
                    text = "Search for subject",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }
            it.invoke()
        }

        AnimatedVisibility(
            query.value.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            IconButton(onClick = {
                query.value = ""
                onValueChange()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

    }
}



@Composable
fun TopicItem(
    item: PartOneResponse.Topic,
    index: Int,
    isExpanded: MutableState<Boolean>,
    hasOverFlow: MutableState<Boolean>
) {
    val degree = animateFloatAsState(
        targetValue = if (!isExpanded.value) -90f else -270f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    val lineCount = remember { mutableStateOf(0) }

    val rowHeight = animateDpAsState(
        targetValue = if (isExpanded.value) 150.dp else 80.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        )
    )

    Box(
        Modifier.fillMaxWidth()
            .appShadow()
            .height(rowHeight.value)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .animateContentSize()
    ) {

        Row(Modifier.padding(12.dp)) {
            AnimatedVisibility(visible = !isExpanded.value) {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "$index",
                        color = MaterialTheme.colors.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = item.name,
                    modifier = Modifier.animateContentSize(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    maxLines = if (isExpanded.value) 10 else 2,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { hasOverFlow.value = it.hasVisualOverflow; lineCount.value = it.lineCount }
                )

                if (!hasOverFlow.value && lineCount.value == 1) {
                    Text("10 questions", fontSize = 12.sp)
                }
            }

            if (hasOverFlow.value || isExpanded.value) {
                IconButton(onClick = { isExpanded.value = !isExpanded.value }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(degree.value),
                        tint = Color.Gray
                    )
                }
            }
        }

        if (item.new == "new") {
            Box(
                Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(bottomStart = 12.dp))
                    .background(Color.Red)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "NEW",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 8.sp
                )
            }
        }
    }
}

const val mock =
    "Parks, tourist attractions (September - December 2024, January - April 2025),  December 2024, January - April 2025),  "

