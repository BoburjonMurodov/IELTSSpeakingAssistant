package com.boboor.speaking.presenter.main

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.boboor.speaking.utils.AppViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.Syntax


/*
    Created by Boburjon Murodov 20/12/24 at 18:21
*/


interface MainScreenContracts {
    interface ViewModel : AppViewModel<UIState, Nothing> {
        fun onEventDispatcher(intent: Intent): Job
        fun init() : Job
    }

    data class UIState(
        val initState: Int = -1
    )

    sealed interface Intent {
        data object OnClickPart : Intent
    }

    interface Directions{
        suspend fun goTopicScreen()
    }
}