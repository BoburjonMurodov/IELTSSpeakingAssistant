package com.boboor.speaking.ui.pages.tabs.main

import com.boboor.speaking.ui.pages.tabs.main.MainScreenContracts.Intent.OnClickPart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import org.orbitmvi.orbit.Container

/*
    Created by Boburjon Murodov 20/12/24 at 18:18
*/



class MainScreenVM(
    private val directions: MainScreenContracts.Directions,
) : MainScreenContracts.ViewModel {

    override fun onEventDispatcher(intent: MainScreenContracts.Intent): Job = intent {
        println("onEventDispatcher on tap")
        when (intent) {
            is OnClickPart -> directions.goTopicScreen(intent.section)
        }
    }

    override fun init(): Job = intent {

    }

    override val container = container<MainScreenContracts.UIState, Nothing>(MainScreenContracts.UIState())
}

