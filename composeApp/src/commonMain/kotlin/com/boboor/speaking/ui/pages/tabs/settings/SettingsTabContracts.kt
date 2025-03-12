package com.boboor.speaking.ui.pages.tabs.settings

import androidx.compose.ui.graphics.Color
import com.boboor.speaking.ui.theme.FontDimension
import com.boboor.speaking.ui.theme.getColor
import com.boboor.speaking.ui.theme.getFontDimension
import com.boboor.speaking.utils.AppViewModel
import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 12/03/25 at 13:45
*/

interface SettingsContracts {
    interface ViewModel : AppViewModel<UIState> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UIState(
        val selectedThemeColor: Color = getColor(),
        val selectedFontDimension: FontDimension = getFontDimension(),
        val isChangeThemeBottomSheetOpen: Boolean = false
    )

    sealed interface Intent {
        data class ChangeThemeColor(val color: Color) : Intent
        data class ChangeFontDimension(val scale: FontDimension) : Intent


        data object OpenChangeThemeBottomSheet : Intent
        data object DismissChangeThemeBottomSheet: Intent
    }
}

data class ColorPlate(
    val name: String,
    val color: Color
)


val colorPlates = listOf(
    ColorPlate("Red", Color(0xFFF44336)),
    ColorPlate("Pink", Color(0xFFE91E63)),
    ColorPlate("Purple", Color(0xFF9C27B0)),
    ColorPlate("Deep Purple", Color(0xFF673AB7)),
    ColorPlate("Indigo", Color(0xFF3F51B5)),
    ColorPlate("Blue", Color(0xFF2196F3)),
    ColorPlate("Light Blue", Color(0xFF03A9F4)),
    ColorPlate("Cyan", Color(0xFF00BCD4)),
    ColorPlate("Teal", Color(0xFF009688)),
    ColorPlate("Green", Color(0xFF4CAF50)),
    ColorPlate("Light Green", Color(0xFF8BC34A)),
    ColorPlate("Lime", Color(0xFFCDDC39)),
    ColorPlate("Yellow", Color(0xFFFFEB3B)),
    ColorPlate("Amber", Color(0xFFFFC107)),
    ColorPlate("Orange", Color(0xFFFF9800)),
    ColorPlate("Deep Orange", Color(0xFFFF5722)),
    ColorPlate("Brown", Color(0xFF795548)),
    ColorPlate("Grey", Color(0xFF9E9E9E)),
    ColorPlate("Blue Grey", Color(0xFF607D8B)),
    ColorPlate("Black", Color(0xFF000000)),
    ColorPlate("White", Color(0xFFFFFFFF))
)