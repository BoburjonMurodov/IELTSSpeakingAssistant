package com.boboor.speaking.ui.pages.screens.splash

import cafe.adriel.voyager.core.model.screenModelScope
import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.utils.enums.UpdateFrequency
import com.boboor.speaking.utils.resultOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


/*
    Created by Boburjon Murodov 16/03/25 at 20:41
*/

class SplashScreenVM(
    private val localStorage: LocalStorage
) : SplashScreenContracts.ViewModel {
    override fun onEvent(intent: SplashScreenContracts.Intent): Job = intent {
        when (intent) {
            SplashScreenContracts.Intent.Init -> updateContent(localStorage.getUpdateFrequency())
        }
    }

    private fun updateContent(value: UpdateFrequency) = intent {
        when (value) {
            UpdateFrequency.EVERY_DAY -> {

            }

            UpdateFrequency.EVERY_APP_OPENING -> {

            }

            UpdateFrequency.NEVER -> {

            }
        }
    }

    override val UIState = MutableStateFlow(SplashScreenContracts.UIState())
}