package com.boboor.speaking.ui.pages.screens.topic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.data.models.CommonTopicItem
import com.boboor.speaking.data.models.toCommonTopicItem
import com.boboor.speaking.data.remote.ApiService
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse
import com.boboor.speaking.data.repository.AppRepository
import com.boboor.speaking.utils.enums.Section
import com.boboor.speaking.utils.resultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


/*
    Created by Boburjon Murodov 20/12/24 at 21:57
*/

class TopicScreenVM(
    private val directions: TopicScreenContracts.Directions,
    private val apiService: ApiService,
    private val localStorage: LocalStorage,
    private val repository: AppRepository
) : TopicScreenContracts.ViewModel {
    override val UIState = MutableStateFlow(TopicScreenContracts.UIState())

    override val searchQuery: MutableState<String> = mutableStateOf("")
    private val questions = ArrayList<CommonTopicItem>()

    private val commonTopicItems: ArrayList<CommonTopicResponse.Topic> = ArrayList()
    private val partTwoItems: ArrayList<PartTwoResponse.PartTwoQuestion> = ArrayList()

    override fun onEventDispatcher(intent: TopicScreenContracts.Intent): Job = intent {
        when (intent) {
            TopicScreenContracts.Intent.OnClickBack -> directions.goBack()
            TopicScreenContracts.Intent.SearchQuery ->
                UIState.update {
                    it.copy(questions = questions.filter {
                        it.question.contains(
                            searchQuery.value,
                            ignoreCase = true
                        )
                    })
                }

            is TopicScreenContracts.Intent.OnCLickTopic -> {
                when (state.value.section) {
                    Section.PART_TWO -> {

                    }

                    else -> {
                        directions.goQuestionsScreen(state.value.section.title, commonTopicItems, intent.index)
                    }
                }
            }

        }
    }

    override fun init(section: Section): Job = intent {
        UIState.update { it.copy(isLoading = true, section = section) }

        when (section) {
            Section.PART_ONE -> getPartOneQuestions()
            Section.PART_TWO -> getPartTwoQuestions()
            Section.PART_THREE -> getPartThreeQuestions()
        }
    }

    private fun getPartOneQuestions() = intent(Dispatchers.IO) {
        val showAnyWay = localStorage.getQuestionsVisibility()

        resultOf { repository.getPartOneQuestions() }
            .onSuccess { result ->
                var index = 0
                result.content.forEach {
                    if (it.value.active || showAnyWay) {
                        commonTopicItems.add(it.value)
                        questions.add(it.value.toCommonTopicItem(index++))
                    }
                }
                UIState.update { it.copy(isLoading = false, questions = questions) }
            }.onFailure {
                println("ERROR ${it.message}")
                UIState.update { it.copy(isLoading = false, error = it.error) }
            }
    }

    private fun getPartTwoQuestions() = intent {
        val showAnyWay = localStorage.getQuestionsVisibility()

        resultOf { repository.getPartTwoQuestions() }
            .onSuccess { result ->
                var index = 0
                result.content.forEach {
                    if (it.value.active || showAnyWay) {
                        partTwoItems.add(it.value)
                        questions.add(it.value.toCommonTopicItem(index++))
                    }
                }
                UIState.update { it.copy(isLoading = false, questions = questions) }
            }.onFailure {
                println("ERROR ${it.message}")
                UIState.update { it.copy(isLoading = false, error = it.error) }
            }
    }


    private fun getPartThreeQuestions() = intent(Dispatchers.IO) {
        val showAnyWay = localStorage.getQuestionsVisibility()

        resultOf { repository.getPartThreeQuestions() }
            .onSuccess { result ->
                var index = 0
                result.content.forEach {
                    if (it.value.active || showAnyWay) {
                        commonTopicItems.add(it.value)
                        questions.add(it.value.toCommonTopicItem(index++))
                    }
                }
                UIState.update { it.copy(isLoading = false, questions = questions) }
            }.onFailure {
                println("ERROR ${it.message}")
                UIState.update { it.copy(isLoading = false, error = it.error) }
            }
    }

}