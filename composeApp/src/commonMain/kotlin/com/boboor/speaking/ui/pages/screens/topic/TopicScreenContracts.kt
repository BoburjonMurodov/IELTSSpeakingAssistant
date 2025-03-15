package com.boboor.speaking.ui.pages.screens.topic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.utils.AppViewModel
import com.boboor.speaking.utils.enums.Section
import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 20/12/24 at 21:56
*/

class TopicScreenContracts {
    interface ViewModel : AppViewModel<UIState> {
        val searchQuery: MutableState<String>

        fun onEventDispatcher(intent: Intent): Job
        fun init(section: Section): Job
    }

    @Immutable
    data class UIState(
        val section: Section = Section.PART_ONE,
        val isLoading: Boolean = false,
        val questions: List<CommonTopicResponse.Topic> = emptyList(),
        val error: String? = null
    )

    sealed interface Intent {
        data object OnClickBack : Intent
        data object SearchQuery : Intent
        data class OnClickTopic(val title: String, val topics: List<CommonTopicResponse.Topic>, val topicIndex: Int) : Intent
    }

    interface Directions {
        suspend fun goQuestionsScreen(title: String, topics: List<CommonTopicResponse.Topic>, topicIndex: Int)
        suspend fun goBack()
    }
}