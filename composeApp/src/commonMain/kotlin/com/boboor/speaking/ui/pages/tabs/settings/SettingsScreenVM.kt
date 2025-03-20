package com.boboor.speaking.ui.pages.tabs.settings

import androidx.compose.ui.graphics.Color
import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.ui.theme.FontDimension
import com.boboor.speaking.ui.theme.getColor
import com.boboor.speaking.ui.theme.getFontDimension
import com.boboor.speaking.ui.theme.setColor
import com.boboor.speaking.ui.theme.setFontDimension
import com.boboor.speaking.utils.enums.UpdateFrequency
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay


/*
    Created by Boburjon Murodov 12/03/25 at 14:01
*/



class SettingsScreenVM(
    private val directions: SettingsContracts.Directions,
    private val localStorage: LocalStorage
) : SettingsContracts.ViewModel {


    override fun onEventDispatcher(intent: SettingsContracts.Intent): Job = intent {
        when (intent) {
            is SettingsContracts.Intent.ChangeFontDimension -> onChangeFontDimension(intent.scale)
            is SettingsContracts.Intent.ChangeThemeColor -> onChangeThemeColor(intent.color)
            is SettingsContracts.Intent.OnClickShowHideQuestions -> onClickShowHideQuestions(intent.show)
            is SettingsContracts.Intent.OnSelectedUpdateFrequency -> onSelectedUpdateFrequency(intent.value)
            SettingsContracts.Intent.OnClickClearCache -> onClickClearCache()
            SettingsContracts.Intent.GoLicenseScreen -> directions.goLicenseScreen()

            SettingsContracts.Intent.OpenChangeThemeBottomSheet -> reduce { state.copy(isChangeThemeBottomSheetOpen = true) }
            SettingsContracts.Intent.DismissChangeThemeBottomSheet -> reduce { state.copy(isChangeThemeBottomSheetOpen = false) }

            SettingsContracts.Intent.DismissConfirmClearCacheBottomSheet -> reduce { state.copy(isConfirmClearCacheBottomSheetOpen = false) }
            SettingsContracts.Intent.OpenConfirmClearCacheBottomSheet -> reduce { state.copy(isConfirmClearCacheBottomSheetOpen = true) }
        }
    }

    private fun onChangeFontDimension(scale: FontDimension) = intent {
        setFontDimension(scale)
        reduce { state.copy(selectedFontDimension = getFontDimension()) }
    }

    private fun onClickShowHideQuestions(show: Boolean) = intent {
        localStorage.setQuestionsVisibility(show)
        reduce { state.copy(showHiddenQuestions = localStorage.getQuestionsVisibility()) }
    }

    private fun onChangeThemeColor(color: Color) = intent {
        setColor(color)
        reduce { state.copy(selectedThemeColor = getColor()) }
    }

    private fun onSelectedUpdateFrequency(value: UpdateFrequency) = intent {
        localStorage.setUpdateFrequency(value = value)
        reduce { state.copy(selectedUpdateFrequency = localStorage.getUpdateFrequency()) }
    }

    private fun onClickClearCache() = intent {
        localStorage.clear()
        delay(300)
        directions.goBackToSplashScreen()
    }

    override val container = container<SettingsContracts.UIState, Nothing>(
        SettingsContracts.UIState(
            showHiddenQuestions = localStorage.getQuestionsVisibility(),
            selectedUpdateFrequency = localStorage.getUpdateFrequency(),
        )
    )

}