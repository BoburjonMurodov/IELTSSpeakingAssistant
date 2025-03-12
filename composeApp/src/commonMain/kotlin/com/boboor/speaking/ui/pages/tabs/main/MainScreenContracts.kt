package com.boboor.speaking.ui.pages.tabs.main

import com.boboor.speaking.utils.AppViewModel
import com.boboor.speaking.utils.Section
import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 20/12/24 at 18:21
*/


interface MainScreenContracts {
    interface ViewModel : AppViewModel<UIState> {
        fun onEventDispatcher(intent: Intent): Job
        fun init() : Job
    }

    data class UIState(
        val initState: Int = -1
    )

    sealed interface Intent {
        data class OnClickPart(val section: Section) : Intent
    }

    interface Directions{
        suspend fun goTopicScreen(section: Section)
    }
}