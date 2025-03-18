package com.boboor.speaking.ui.pages.screens.commonQuestions

import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.utils.AppViewModel
import com.boboor.speaking.utils.enums.Section
import io.ktor.util.reflect.Type
import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 18/03/25 at 20:59
*/

interface CommonQuestionContracts {
    interface ViewModel: AppViewModel<UIState, Nothing>{
        fun onEventDispatcher(intent: Intent): Job
    }

    data class UIState(
        val questions: List<CommonTopicResponse.Topic> = emptyList(),
    )

    interface Intent{
        data object OnClickBack: Intent
        data class Init(val section: Section): Intent
        data class OnClickQuestion(val index: Int): Intent
    }

    interface Directions{
        suspend fun navigateBack()
        suspend fun navigateToDetail(index: Int)
    }
}