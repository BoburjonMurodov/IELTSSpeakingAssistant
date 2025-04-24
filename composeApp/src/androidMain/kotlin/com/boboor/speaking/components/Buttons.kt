package com.boboor.speaking.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.boboor.speaking.ui.theme.AppTheme


/*
    Created by Boburjon Murodov 24/04/25 at 12:36
*/


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Text(text = text)
    }
}



@Preview
@Composable
private fun ContentPreview() {
    AppTheme {
        PrimaryButton(
            text = "Primary Button",
            onClick = {}
        )
    }
}
