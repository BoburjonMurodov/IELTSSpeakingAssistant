package com.boboor.speaking.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.sofia_pro_bold
import ieltsspeakingassistant.composeapp.generated.resources.sofia_pro_medium
import ieltsspeakingassistant.composeapp.generated.resources.sofia_pro_regular
import ieltsspeakingassistant.composeapp.generated.resources.sofie_pro_semi_bold
import kotlinx.coroutines.launch
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
    Font(Res.font.sofie_pro_semi_bold, FontWeight.SemiBold),
    Font(Res.font.sofia_pro_bold, FontWeight.Bold),
    Font(Res.font.sofia_pro_medium, FontWeight.Medium),
    Font(Res.font.sofia_pro_regular, FontWeight.Normal),
)

@Composable
fun getAppTypography(fontDimension: Animatable<Float, AnimationVector1D>) =
    MaterialTheme.typography.copy(
        displayLarge = MaterialTheme.typography.displayLarge.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.displayLarge.fontSize * fontDimension.value
        ),
        displayMedium = MaterialTheme.typography.displayMedium.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.displayMedium.fontSize * fontDimension.value
        ),
        displaySmall = MaterialTheme.typography.displaySmall.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.displaySmall.fontSize * fontDimension.value
        ),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize * fontDimension.value
        ),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize * fontDimension.value
        ),
        bodySmall = MaterialTheme.typography.bodySmall.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.bodySmall.fontSize * fontDimension.value
        ),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.headlineLarge.fontSize * fontDimension.value
        ),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.headlineMedium.fontSize * fontDimension.value
        ),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.headlineSmall.fontSize * fontDimension.value
        ),
        titleLarge = MaterialTheme.typography.titleLarge.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.titleLarge.fontSize * fontDimension.value
        ),
        titleMedium = MaterialTheme.typography.titleMedium.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.titleMedium.fontSize * fontDimension.value
        ),
        titleSmall = MaterialTheme.typography.titleSmall.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.titleSmall.fontSize * fontDimension.value
        ),
        labelLarge = MaterialTheme.typography.labelLarge.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.labelLarge.fontSize * fontDimension.value
        ),
        labelMedium = MaterialTheme.typography.labelMedium.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.labelMedium.fontSize * fontDimension.value
        ),
        labelSmall = MaterialTheme.typography.labelSmall.copy(
            fontFamily = MontFontFamily(),
            fontSize = MaterialTheme.typography.labelSmall.fontSize * fontDimension.value
        )
    )


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val fontDimension = remember { mutableStateOf(getFontDimension()) }
    val coroutineScope = rememberCoroutineScope()
    val fontDimension = remember { Animatable(initialValue = getFontDimension().scale) }

    val typography = getAppTypography(fontDimension)

    val colorCode = remember { mutableStateOf(0L) }
    val seedColor = remember(colorCode.value) { mutableStateOf(getColor()) }

    DisposableEffect(Unit) {
        val colorListener = colorSettings.addLongListener("color", Color.Red.value.toLong()) { newColor ->
            seedColor.value = Color(newColor)
            colorCode.value = newColor
        }

        val fontScaleListener = colorSettings.addStringListener("fontDimension", FontDimension.MEDIUM.name) {
            coroutineScope.launch {
                fontDimension.animateTo(
                    FontDimension.valueOf(it).scale,
                    animationSpec = tween(1000)
                )
            }
        }

        onDispose {
            colorListener.deactivate()
            fontScaleListener.deactivate()
        }
    }


    DynamicMaterialTheme(
        seedColor = seedColor.value,
        animate = true,
        animationSpec = tween(1000),
        content = content,
        shapes = MaterialTheme.shapes,
        typography = typography,
        withAmoled = true,
        style = PaletteStyle.Content
    )
}

private val colorSettings = MakeObservableSettings(Settings())

fun getColor(): Color {
    val colorCode = colorSettings.get<Long>("color") ?: Color.Red.value.toLong()
//    println("colorCode $colorCode")

    return Color(colorCode.toULong())
//    return Color.Red
}


fun setColor(color: Color) {
    val colorCode = color.value
    colorSettings.putLong("color", colorCode.toLong())
}

fun getFontDimension(): FontDimension {
    val dimension = colorSettings.getString("fontDimension", FontDimension.MEDIUM.name)
    return FontDimension.valueOf(dimension)
}

fun setFontDimension(dimension: FontDimension) {
    colorSettings.putString("fontDimension", dimension.name)
}
