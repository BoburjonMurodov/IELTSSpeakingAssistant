package com.boboor.speaking.ui.pages.tabs.settings

import com.boboor.speaking.ui.theme.getColor
import com.boboor.speaking.ui.theme.getFontDimension
import com.boboor.speaking.ui.theme.setColor
import com.boboor.speaking.ui.theme.setFontDimension
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow


/*
    Created by Boburjon Murodov 12/03/25 at 14:01
*/



class SettingsScreenVM : SettingsContracts.ViewModel {
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

            SettingsContracts.Intent.OpenChangeThemeBottomSheet -> reduce {
                UIState.value.copy(isChangeThemeBottomSheetOpen = true)
            }

            SettingsContracts.Intent.DismissChangeThemeBottomSheet -> reduce {
                UIState.value.copy(isChangeThemeBottomSheetOpen = false)
            }
        }
    }

    override val UIState = MutableStateFlow(SettingsContracts.UIState())
}