package com.boboor.speaking.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.dinroundpro
import ieltsspeakingassistant.composeapp.generated.resources.dinroundpro_black
import ieltsspeakingassistant.composeapp.generated.resources.dinroundpro_bold
import ieltsspeakingassistant.composeapp.generated.resources.dinroundpro_light
import ieltsspeakingassistant.composeapp.generated.resources.dinroundpro_medi
import org.jetbrains.compose.resources.Font

//import androidx.compose.ui.text.font.FontFamily


/*
    Created by Boburjon Murodov 24/04/25 at 11:07
*/

@Immutable
data class DuoTypography(
    val title: TextStyle,
    val heading: TextStyle,
    val subHeading: TextStyle,
    val body: TextStyle,
    val bodySmall: TextStyle,
    val bodyExtraSmall: TextStyle
)

val DefaultDuoTypography = DuoTypography(
    title = TextStyle.Default,
    heading = TextStyle.Default,
    subHeading = TextStyle.Default,
    body = TextStyle.Default,
    bodySmall = TextStyle.Default,
    bodyExtraSmall = TextStyle.Default
)


//@Composable
@Composable
fun PrimaryTypography() = FontFamily(
    Font(Res.font.dinroundpro_light, FontWeight.Light),
    Font(Res.font.dinroundpro, FontWeight.Normal),
    Font(Res.font.dinroundpro_bold, FontWeight.Bold),
    Font(Res.font.dinroundpro_medi, FontWeight.Medium),
    Font(Res.font.dinroundpro_black, FontWeight.Black)
)


@Composable
fun getDuolingoTypography(fontDimension: Animatable<Float, AnimationVector1D>): DuoTypography {
    val primaryTypography = PrimaryTypography()
    return DuoTypography(
        title = TextStyle(
            fontFamily = primaryTypography,
            fontWeight = FontWeight.Black,
            fontSize = 36.sp * fontDimension.value,
            lineHeight = 44.sp * fontDimension.value,
        ),
        heading = TextStyle(
            fontFamily = primaryTypography,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp * fontDimension.value,
        ),
        subHeading = TextStyle(
            fontFamily = primaryTypography,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp * fontDimension.value,
        ),
        body = TextStyle(
            fontFamily = primaryTypography,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp * fontDimension.value,
        ),
        bodySmall = TextStyle(
            fontFamily = primaryTypography,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp * fontDimension.value,
        ),
        bodyExtraSmall = TextStyle(
            fontFamily = primaryTypography,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp * fontDimension.value,
        )
    )
}