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
        val isChangeThemeBottomSheetOpen: Boolean = false,
        val showHiddenQuestions: Boolean = false
    )

    sealed interface Intent {
        data class ChangeThemeColor(val color: Color) : Intent
        data class ChangeFontDimension(val scale: FontDimension) : Intent


        data object OpenChangeThemeBottomSheet : Intent
        data object DismissChangeThemeBottomSheet: Intent

        data object OnClickClearCache: Intent

        data class OnClickShowHideQuestions(val show: Boolean) : Intent
    }


    interface Directions{
        suspend fun goBackToSplashScreen()
    }
}


val seedColors = listOf(
    Color(0xFFF44336),
    Color(0xFFE91E63),
    Color(0xFF9C27B0),
    Color(0xFF673AB7),
    Color(0xFF3F51B5),
    Color(0xFF2196F3),
    Color(0xFF03A9F4),
    Color(0xFF00BCD4),
    Color(0xFF009688),
    Color(0xFF4CAF50),
    Color(0xFF8BC34A),
    Color(0xFFCDDC39),
    Color(0xFFFFEB3B),
    Color(0xFFFFC107),
    Color(0xFFFF9800),
    Color(0xFFFF5722),
    Color(0xFF795548),
    Color(0xFF9E9E9E),
    Color(0xFF607D8B),
    Color(0xFF000000),
    Color(0xFFFFFFFF)
)