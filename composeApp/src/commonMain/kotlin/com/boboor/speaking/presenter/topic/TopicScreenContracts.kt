package com.boboor.speaking.presenter.topic

import androidx.compose.runtime.MutableState
import com.boboor.speaking.data.models.CommonData
import com.boboor.speaking.utils.AppViewModel
import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 20/12/24 at 21:56
*/

class TopicScreenContracts {
    interface ViewModel : AppViewModel<UIState, Nothing> {
        val searchQuery: MutableState<String>

        fun onEventDispatcher(intent: Intent): Job
        fun init(): Job
    }
    data class UIState(
        val isLoading: Boolean = false,
        val questions: List<CommonData.Topic> = emptyList(),
    )

    sealed interface Intent {
        data object OnClickBack : Intent
        data object SearchQuery : Intent
    }

    interface Directions {
        suspend fun goTopicScreen()
        suspend fun goBack()
    }
}