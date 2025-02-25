package com.boboor.speaking.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.materialkolor.DynamicMaterialTheme
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.mont_bold
import ieltsspeakingassistant.composeapp.generated.resources.mont_medium
import ieltsspeakingassistant.composeapp.generated.resources.mont_regular
import ieltsspeakingassistant.composeapp.generated.resources.mont_semibold

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
fun MontFontFamily() = FontFamily(
    Font(Res.font.mont_semibold, FontWeight.SemiBold),
    Font(Res.font.mont_bold, FontWeight.Bold),
    Font(Res.font.mont_medium, FontWeight.Medium),
    Font(Res.font.mont_regular, FontWeight.Normal),
)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = MaterialTheme.typography.copy(
        displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = MontFontFamily()),
        displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = MontFontFamily()),
        displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = MontFontFamily()),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = MontFontFamily()),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = MontFontFamily()),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = MontFontFamily()),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = MontFontFamily()),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = MontFontFamily()),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = MontFontFamily()),
        titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = MontFontFamily()),
        titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = MontFontFamily()),
        titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = MontFontFamily()),
        labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = MontFontFamily()),
        labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = MontFontFamily()),
        labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = MontFontFamily()),
    )

    DynamicMaterialTheme(
        seedColor = Color(0xff268334),
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


