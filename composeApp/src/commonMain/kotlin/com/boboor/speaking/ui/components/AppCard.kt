package com.boboor.speaking.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.boboor.speaking.getScreenWidth
import com.boboor.speaking.utils.debounceClickable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


/*
    Created by Boburjon Murodov 15/03/25 at 12:37
*/


@Composable
fun AppCard(
    title: String,
    image: DrawableResource,
    takeFull: Boolean = false,
    onClick: () -> Unit = {}
) {
    val screenWidth = getScreenWidth() - 72.dp


    Column(
        modifier = Modifier
            .width(if (takeFull) screenWidth + 24.dp else screenWidth / 2)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .debounceClickable { onClick.invoke() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(image),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}