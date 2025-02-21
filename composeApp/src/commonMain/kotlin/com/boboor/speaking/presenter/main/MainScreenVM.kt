package com.boboor.speaking.presenter.main

import com.boboor.speaking.data.remote.ApiService
import com.boboor.speaking.presenter.main.MainScreenContracts.Intent.OnClickPart
import kotlinx.coroutines.Job
import kotlin.text.Typography.section

/*
    Created by Boburjon Murodov 20/12/24 at 18:18
*/



class MainScreenVM(
    private val directions: MainScreenContracts.Directions,
) : MainScreenContracts.ViewModel{

    init {
        println("MainScreenVM init")
    }

    override fun onEventDispatcher(intent: MainScreenContracts.Intent): Job = intent{
        println("****onEventDispatcher")

        when(intent){
            is OnClickPart -> directions.goTopicScreen(intent.section)
        }
    }

    override fun init(): Job = intent{

    }

    override val container = container<MainScreenContracts.UIState, Nothing>(MainScreenContracts.UIState())
}