package com.boboor.speaking.presenter.main

import com.boboor.speaking.data.remote.ApiService
import kotlinx.coroutines.Job

/*
    Created by Boburjon Murodov 20/12/24 at 18:18
*/



class MainScreenVM(
    private val directions: MainScreenContracts.Directions,
//    private val apiService: ApiService
) : MainScreenContracts.ViewModel{


    override fun onEventDispatcher(intent: MainScreenContracts.Intent): Job = intent{
        when(intent){
            MainScreenContracts.Intent.OnClickPart -> directions.goTopicScreen()
        }
    }

    override fun init(): Job = intent{
//        apiService.getPartOneQuestions()
    }

    override val container = container<MainScreenContracts.UIState, Nothing>(MainScreenContracts.UIState())
}