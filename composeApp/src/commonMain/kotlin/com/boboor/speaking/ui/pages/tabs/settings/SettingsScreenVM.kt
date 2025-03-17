package com.boboor.speaking.ui.pages.tabs.settings

import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.ui.theme.getColor
import com.boboor.speaking.ui.theme.getFontDimension
import com.boboor.speaking.ui.theme.setColor
import com.boboor.speaking.ui.theme.setFontDimension
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow


/*
    Created by Boburjon Murodov 12/03/25 at 14:01
*/



class SettingsScreenVM(
    private val directions: SettingsContracts.Directions,
    private val localStorage: LocalStorage
) : SettingsContracts.ViewModel {


    override fun onEventDispatcher(intent: SettingsContracts.Intent): Job = intent {
        when (intent) {
            is SettingsContracts.Intent.ChangeFontDimension -> {
                setFontDimension(intent.scale)
                reduce {
                    state.copy(
                        selectedFontDimension = getFontDimension()
                    )
                }
            }

            is SettingsContracts.Intent.ChangeThemeColor -> {
                setColor(intent.color)
                reduce {
                    state.copy(
                        selectedThemeColor = getColor()
                    )
                }
            }

            is SettingsContracts.Intent.OnClickShowHideQuestions -> {
                localStorage.setQuestionsVisibility(intent.show)
                reduce {
                    state.copy(showHiddenQuestions = localStorage.getQuestionsVisibility())
                }
            }

            is SettingsContracts.Intent.OnSelectedUpdateFrequency -> {
                localStorage.setUpdateFrequency(value = intent.value)
                reduce { state.copy(selectedUpdateFrequency = localStorage.getUpdateFrequency()) }
            }


            SettingsContracts.Intent.OpenChangeThemeBottomSheet -> reduce {
                state.copy(isChangeThemeBottomSheetOpen = true)
            }

            SettingsContracts.Intent.DismissChangeThemeBottomSheet -> reduce {
                state.copy(isChangeThemeBottomSheetOpen = false)
            }

            SettingsContracts.Intent.OnClickClearCache -> {
                localStorage.clear()
                delay(100)
                directions.goBackToSplashScreen()
            }

            SettingsContracts.Intent.GoLicenseScreen -> directions.goLicenseScreen()
        }
    }

    override val container = container<SettingsContracts.UIState, Nothing>(
        SettingsContracts.UIState(
            showHiddenQuestions = localStorage.getQuestionsVisibility(),
            selectedUpdateFrequency = localStorage.getUpdateFrequency(),
        )
    )

}