package com.boboor.speaking.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color


/*
    Created by Boburjon Murodov 24/04/25 at 10:58
*/

//  Primary Colors
val duoGreenColor = Color(0xff58CC02)
val duoBlueColor = Color(0xff1CB0F6)
val duoRedColor = Color(0xffFF4B4B)
val duoYellowColor = Color(0xffFFC800)
val duoPurpleColor = Color(0xffCE82FF)

//  Hover States
val duoGreenHoverColor = Color(0xff4CB200)
val duoBlueHoverColor = Color(0xff18A0E0)
val duoRedHoverColor = Color(0xffEA2B2B)
val duoYellowHoverColor = Color(0xffEBB200)
val duoPurpleHoverColor = Color(0xffBB6BE0)


//  Neutrals
val duoWhiteColor = Color(0xffFFFFFF)
val duoGray100Color = Color(0xffAFAFAF)
val duoGray200Color = Color(0xff777777)
val duoGray300Color = Color(0xff4B4B4B)
val duoGray400Color = Color(0xff333333)
val duoBlackColor = Color(0xff1A1D28)

//  Theme colors
val backgroundLight = Color(0xffFFFFFF)
val backgroundDark = Color(0xff1A1D28)

val secondaryBackgroundLight = Color(0xffF7F7F7)
val secondaryBackgroundDark = Color(0xff2E3241)

val cardBackgroundLight = Color(0xffFFFFFF)
val cardBackgroundDark = Color(0xff2E3241)

val textColorLight = Color(0xff1A1D28)
val textColorDark = Color(0xffFFFFFF)

val secondaryTextColorLight = Color(0xff1A1D28)
val secondaryTextColorDark = Color(0xffAFAFAF)

@Immutable
data class DuolingoColors(
    val background: Color,
    val secondaryBackground: Color,
    val cardBackground: Color,
    val textColor: Color,
    val secondaryTextColor: Color,
    val duoGreen: Color = duoGreenColor,
    val duoBlue: Color = duoBlueColor,
    val duoRed: Color = duoRedColor,
    val duoYellow: Color = duoYellowColor,
    val duoPurple: Color = duoPurpleColor,
    val duoGreenHover: Color = duoGreenHoverColor,
    val duoBlueHover: Color = duoBlueHoverColor,
    val duoRedHover: Color = duoRedHoverColor,
    val duoYellowHover: Color = duoYellowHoverColor,
    val duoPurpleHover: Color = duoPurpleHoverColor
)



