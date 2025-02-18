package com.boboor.speaking.presenter.topic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.boboor.speaking.data.models.CommonData
import com.boboor.speaking.data.remote.ApiService
import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 20/12/24 at 21:57
*/

class TopicScreenVM(
    private val directions: TopicScreenContracts.Directions,
    private val apiService: ApiService
) : TopicScreenContracts.ViewModel {
    override val searchQuery: MutableState<String> = mutableStateOf("")
    private val questions = ArrayList<CommonData.Topic>()

    override fun onEventDispatcher(intent: TopicScreenContracts.Intent): Job = intent {
        when (intent) {
            TopicScreenContracts.Intent.OnClickBack -> directions.goBack()
            TopicScreenContracts.Intent.SearchQuery -> reduce {
                state.copy(questions = questions.filter {
                    it.name.contains(
                        searchQuery.value,
                        ignoreCase = true
                    )
                })
            }
        }
    }

    override fun init(): Job = intent {
        reduce { state.copy(isLoading = true) }

        println("---init called")

        apiService.getPartOneQuestions().onSuccess { it ->

            it.content.forEach {
                if (it.value.active)
                    questions.add(it.value)
            }

            reduce { state.copy(isLoading = false, questions = questions) }
        }.onFailure {
            reduce { state.copy(isLoading = false) }
        }

    }

    override val container = container<TopicScreenContracts.UIState, Nothing>(TopicScreenContracts.UIState())
}