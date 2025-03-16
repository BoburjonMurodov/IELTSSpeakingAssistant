package com.boboor.speaking.ui.pages.screens.splash

import com.boboor.speaking.utils.AppViewModel
import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 16/03/25 at 20:38
*/

interface SplashScreenContracts {
    interface ViewModel : AppViewModel<UIState> {
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UIState(
        val isLoading: Boolean = false
    )

    interface Intent {
        object Init : Intent
    }

    interface Directions{
        suspend fun navigateHomeScreen()
    }
}