package com.boboor.speaking.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/*
    Created by Boburjon Murodov 20/02/25 at 17:31
*/

@Composable
fun SearchInput(
    query: MutableState<String>,
    onValueChange: () -> Unit
) {
    val focusRequest = FocusRequester()

    LaunchedEffect(Unit) {
        focusRequest.requestFocus()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                BorderStroke(width = 2.dp, color = Color(0xff939393).copy(alpha = 0.2f)),
                shape = RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        ) {
            if (query.value.isEmpty()) {
                Text(
                    text = "Search...",
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

