package com.boboor.speaking.ui.screens.settings

import com.boboor.speaking.ui.theme.getColor
import com.boboor.speaking.ui.theme.getFontDimension
import com.boboor.speaking.ui.theme.setColor
import com.boboor.speaking.ui.theme.setFontDimension
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow


/*
    Created by Boburjon Murodov 12/03/25 at 14:01
*/



class SettingsVM : SettingsContracts.ViewModel {
    override fun onEventDispatcher(intent: SettingsContracts.Intent): Job = intent {
        when (intent) {
            is SettingsContracts.Intent.ChangeFontDimension -> {
                setFontDimension(intent.scale)
                reduce {
                    UIState.value.copy(
                        selectedFontDimension = getFontDimension()
                    )
                }
            }
            is SettingsContracts.Intent.ChangeThemeColor -> {
                setColor(intent.color)
                reduce {
                    UIState.value.copy(
                        selectedThemeColor = getColor()
                    )
                }
            }

        }
    }

    override val UIState = MutableStateFlow(SettingsContracts.UIState())
}