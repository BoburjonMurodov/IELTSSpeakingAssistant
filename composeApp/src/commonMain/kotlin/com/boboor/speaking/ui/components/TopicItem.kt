package com.boboor.speaking.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boboor.speaking.data.models.CommonTopicItem
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.utils.debounceClickable
import com.valentinilk.shimmer.shimmer
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource


/*
    Created by Boburjon Murodov 22/02/25 at 00:20
*/

private val SHIMMER_COLOR = Color.Gray.copy(.3f)

@Composable
fun TopicItem(
    item: CommonTopicItem,
//    item: CommonTopicResponse.Topic,
//    index: Int,
    isExpanded: MutableState<Boolean>,
    hasOverFlow: MutableState<Boolean>,
    searchQuery: String = "",
    onClick: () -> Unit = {}
) {
    val degree = animateFloatAsState(
        targetValue = if (!isExpanded.value) -90f else -270f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    val maxLines = animateIntAsState(if (isExpanded.value) 10 else 2)
    val lineCount = remember { mutableStateOf(0) }

    Row(
        modifier = Modifier.then(
            if (item.active)
                Modifier
            else Modifier.alpha(0.5f)
        )
    ) {
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "${item.order}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge,
            )
        }

        Box(
            Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .debounceClickable { onClick() }
                .animateContentSize(spring(stiffness = Spring.StiffnessLow))
        ) {

            Row(Modifier.padding(16.dp).height(IntrinsicSize.Min)) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxSize(0.9f)
                ) {
                    Text(
                        text = getHighLightedText(item.question, searchQuery),
                        fontWeight = FontWeight.W600,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = maxLines.value,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = {
                            hasOverFlow.value = it.hasVisualOverflow
                            lineCount.value = it.lineCount
                        }
                    )

                    Spacer(Modifier.weight(1f))
                    Text("${item.question} questions", fontSize = 12.sp)
                }

                if (hasOverFlow.value || isExpanded.value) {
                    IconButton(onClick = { isExpanded.value = !isExpanded.value }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer { rotationZ = degree.value },
                            tint = Color.Gray
                        )
                    }
                } else
                    Spacer(Modifier.size(32.dp))
            }

            if (item.new) {
                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(bottomStart = 12.dp))
                        .background(MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        text = "NEW",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onError,
                        fontSize = 8.sp
                    )
                }
            }
        }
    }
}


@Composable
fun TopicItemShimmer() {
    Row(
        modifier = Modifier
            .shimmer()
    ) {
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {

        }

        Box(
            Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .animateContentSize(spring(stiffness = Spring.StiffnessLow))
        ) {

            Row(Modifier.padding(16.dp)) {
                Column(
                    Modifier.fillMaxSize(0.8f)
                ) {
                    Text(
                        text = "",
                        fontWeight = FontWeight.W600,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                            .height(12.dp)
                            .background(SHIMMER_COLOR)
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "",
                        fontWeight = FontWeight.W600,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(.6f)
                            .height(12.dp)
                            .background(SHIMMER_COLOR)
                    )

                    Spacer(Modifier.height(8.dp))
                    Text(
                        "", fontSize = 12.sp,
                        modifier = Modifier.width(100.dp)
                            .height(8.dp)
                            .background(SHIMMER_COLOR)
                    )
                }

            }

            Box(
                Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(bottomStart = 12.dp))
                    .background(SHIMMER_COLOR)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        .width(16.dp),
                    text = "  ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onError,
                    fontSize = 8.sp
                )
            }
        }
    }
}


@Composable
fun getHighLightedText(fullText: String, searchText: String) = buildAnnotatedString {
    val startIndex = fullText.indexOf(searchText, ignoreCase = true)

    if (startIndex != -1) {
        append(fullText.substring(0, startIndex))
        withStyle(style = SpanStyle(background = MaterialTheme.colorScheme.primary.copy(alpha = .3f))) {
            append(fullText.substring(startIndex, startIndex + searchText.length))
        }
        append(fullText.substring(startIndex + searchText.length))
    } else {
        append(fullText)
    }
}
