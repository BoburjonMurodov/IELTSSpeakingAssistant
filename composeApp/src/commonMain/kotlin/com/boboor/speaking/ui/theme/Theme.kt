package com.boboor.speaking.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.materialkolor.DynamicMaterialTheme
import ieltsspeakingassistant.composeapp.generated.resources.Lato_Black
import ieltsspeakingassistant.composeapp.generated.resources.Lato_Bold
import ieltsspeakingassistant.composeapp.generated.resources.Lato_Light
import ieltsspeakingassistant.composeapp.generated.resources.Lato_Regular
import ieltsspeakingassistant.composeapp.generated.resources.Lato_Thin
import ieltsspeakingassistant.composeapp.generated.resources.Res

import org.jetbrains.compose.resources.Font


/*
    Created by Boburjon Murodov 19/12/24 at 23:33
*/

//private val DarkColorScheme = darkColors(
//    primary = Color(0xFFBB86FC),
//    primaryVariant = Color(0xFF3700B3),
//    secondary = Color(0xFF03DAC5),
//    surface = Color.Black,
//
//)
//
//private val LightColorScheme = darkColors(
//    primary = Color(0xFF6200EE),
//    primaryVariant = Color(0xFF3700B3),
//    secondary = Color(0xFF03DAC5),
//    surface = Color.White
//)

@Composable
fun LatoFontFamily() = FontFamily(
    Font(resource = Res.font.Lato_Bold, weight = FontWeight.Bold),
    Font(Res.font.Lato_Black, FontWeight.Black),
    Font(Res.font.Lato_Light, FontWeight.Light),
    Font(Res.font.Lato_Thin, FontWeight.Thin),
)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = MaterialTheme.typography.copy(
        displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = LatoFontFamily()),
        displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = LatoFontFamily()),
        displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = LatoFontFamily()),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = LatoFontFamily()),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = LatoFontFamily()),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = LatoFontFamily()),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = LatoFontFamily()),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = LatoFontFamily()),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = LatoFontFamily()),
        titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = LatoFontFamily()),
        titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = LatoFontFamily()),
        titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = LatoFontFamily()),
        labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = LatoFontFamily()),
        labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = LatoFontFamily()),
        labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = LatoFontFamily()),
    )

    DynamicMaterialTheme(
        seedColor = Color.Green,
        animate = true,
        content = content,
        shapes = MaterialTheme.shapes,
        typography = typography,
    )


//    MaterialTheme(
//        colors = MaterialTheme.colors,
//        typography = MaterialTheme.typography,
//        shapes = MaterialTheme.shapes,
//        content = content,
//    )

}


