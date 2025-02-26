package com.boboor.speaking.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.boboor.speaking.Platform
import com.boboor.speaking.getPlatform
import com.boboor.speaking.utils.debounceClickable
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource


/*
    Created by Boburjon Murodov 20/02/25 at 17:59
*/


@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    showSearch: Boolean = true,
    isSearchVisible: Boolean = true,
    onClickBack: () -> Unit,
    onClickSearch: () -> Unit,
) {
    val backButtonWith = remember { mutableStateOf(0) }

    Box(
        modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }
                .clip(RoundedCornerShape(6.dp))
                .debounceClickable(isRippleAvailable = false) { onClickBack.invoke() }
                .padding(6.dp)
                .zIndex(1f)
                .align(Alignment.CenterStart),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(Res.drawable.ic_back),
                contentDescription = null,
            )

            Text(
                text = "Back",
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Text(
            modifier = Modifier.basicMarquee().align(Alignment.Center).padding(start = backButtonWith.value.dp),
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W600),
            color = MaterialTheme.colorScheme.onSurface,
        )

        if (showSearch)
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                enabled = isSearchVisible,
                onClick = { onClickSearch.invoke() },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    modifier = Modifier.size(24.dp),
                    contentDescription = null,
                )
            }

    }

}