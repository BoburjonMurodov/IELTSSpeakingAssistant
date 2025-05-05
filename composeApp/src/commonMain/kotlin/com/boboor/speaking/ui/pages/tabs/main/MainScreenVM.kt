package com.boboor.speaking.ui.pages.tabs.main

import com.boboor.speaking.data.repository.TopicRepository
import com.boboor.speaking.ui.pages.tabs.main.MainScreenContracts.Intent.OnClickPart
import com.boboor.speaking.ui.pages.tabs.main.MainScreenContracts.SideEffect
import com.boboor.speaking.utils.resultOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

/*
    Created by Boburjon Murodov 20/12/24 at 18:18
*/



class MainScreenVM(
    private val directions: MainScreenContracts.Directions,
//    private val repository: TopicRepository
) : MainScreenContracts.ViewModel {

    override fun onEventDispatcher(intent: MainScreenContracts.Intent): Job = intent {
        println("onEventDispatcher on tap")
        when (intent) {
            is OnClickPart -> directions.goTopicScreen(intent.section)
        }
    }

    override fun init(): Job = intent {
        resultOf {
            delay(300)
            postSideEffect(SideEffect.Message("WTF:: Started loading"))
            println("Started loading")
//            repository.syncAllTopics()
        }.onFailure {
            println("WTF:: ${it.message}" ?:"WTF:: Error")
            postSideEffect(SideEffect.Error(it.message ?:"Error"))
        }.onSuccess {
            println("WTF:: Loaded all data")
            postSideEffect(SideEffect.Message("Loaded all data"))
        }
    }

    override val container = container<MainScreenContracts.UIState, SideEffect>(MainScreenContracts.UIState())
}

