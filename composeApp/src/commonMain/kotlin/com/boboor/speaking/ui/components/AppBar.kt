package com.boboor.speaking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource


/*
    Created by Boburjon Murodov 20/02/25 at 17:59
*/


@Composable
fun AppBar(
    title: String,

    showSearch: Boolean = true,
    isSearchVisible: Boolean = true,
    onClickBack: () -> Unit,
    onClickSearch: () -> Unit,
) {
    Row(
        Modifier
            .shadow(10.dp, spotColor = MaterialTheme.colorScheme.onSurface)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 15.dp,
                    spotColor = Color.Black.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .clickable { onClickBack.invoke() }
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
            modifier = Modifier.basicMarquee(),
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W600),
            color = MaterialTheme.colorScheme.onSurface,
        )

        if (showSearch)
            IconButton(
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