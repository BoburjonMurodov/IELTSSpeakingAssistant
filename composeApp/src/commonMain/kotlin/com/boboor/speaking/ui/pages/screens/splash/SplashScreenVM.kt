package com.boboor.speaking.ui.pages.screens.splash

import cafe.adriel.voyager.core.model.screenModelScope
import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.data.repository.AppRepository
import com.boboor.speaking.utils.enums.UpdateFrequency
import com.boboor.speaking.utils.resultOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/*
    Created by Boburjon Murodov 16/03/25 at 20:41
*/

class SplashScreenVM(
    private val localStorage: LocalStorage,
    private val repository: AppRepository,
    private val directions: SplashScreenContracts.Directions
) : SplashScreenContracts.ViewModel {
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
                    val part1 = async { resultOf { repository.getPartOneQuestions(false) } }
                    val part2 = async { resultOf { repository.getPartTwoQuestions(false) } }
                    val part3 = async { resultOf { repository.getPartThreeQuestions(false) } }

                    awaitAll(part1, part2, part3)

                    reduce { state.copy(isLoading = false) }

                    directions.navigateHomeScreen()
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