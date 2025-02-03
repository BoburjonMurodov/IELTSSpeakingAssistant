package com.boboor.speaking.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


/*
    Created by Boburjon Murodov 19/12/24 at 23:33
*/

private val DarkColorScheme = darkColors(
    primary = Color(0xFFBB86FC),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC5),
    surface = Color.Black,

)


private val LightColorScheme = darkColors(
    primary = Color(0xFF6200EE),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC5),
    surface = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content,
    )

}


