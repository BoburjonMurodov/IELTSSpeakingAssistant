package com.boboor.speaking.ui.pages.screens.splash

import cafe.adriel.voyager.core.model.screenModelScope
import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.data.repository.TopicRepository
import com.boboor.speaking.utils.enums.UpdateFrequency
import com.boboor.speaking.utils.resultOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/*
    Created by Boburjon Murodov 16/03/25 at 20:41
*/

class SplashScreenVM(
    private val localStorage: LocalStorage,
//    private val repository: AppRepository,
//    private val topicRepository: TopicRepository,
    private val directions: SplashScreenContracts.Directions,
) : SplashScreenContracts.ViewModel {

    init {
        screenModelScope.launch {
            delay(300)
            onEventDispatcher(SplashScreenContracts.Intent.Init)
        }
    }

    override fun onEventDispatcher(intent: SplashScreenContracts.Intent): Job = intent {
        when (intent) {
            SplashScreenContracts.Intent.Init -> updateContent(localStorage.getUpdateFrequency())
        }
    }

    private fun updateContent(value: UpdateFrequency) = intent {
        when (value) {
            UpdateFrequency.EVERY_DAY -> {
                delay(1000)
                directions.navigateHomeScreen()
            }

            UpdateFrequency.EVERY_APP_OPENING -> {
                screenModelScope.launch {
                    reduce { state.copy(isLoading = true) }
                    resultOf {
//                        delay(1000)
//                        topicRepository.syncAllTopics()
                    }.onSuccess {
                        directions.navigateHomeScreen()
                    }.onFailure {
                        reduce {
                            state.copy(
                                isLoading = false,
                                error = it.message
                            )
                        }

                    }

                }
            }

            UpdateFrequency.NEVER -> {
                delay(1000)
                directions.navigateHomeScreen()
            }
        }
    }

    override val container = container<SplashScreenContracts.UIState, Nothing>(SplashScreenContracts.UIState())

}