package com.boboor.speaking.ui.pages.screens.topic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.data.models.CommonTopicItem
import com.boboor.speaking.data.models.toCommonTopicItem
import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse
import com.boboor.speaking.data.repository.AppRepository
import com.boboor.speaking.utils.enums.Section
import com.boboor.speaking.utils.resultOf
import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 20/12/24 at 21:57
*/

class TopicScreenVM(
    private val directions: TopicScreenContracts.Directions,
    private val localStorage: LocalStorage,
    private val repository: AppRepository
) : TopicScreenContracts.ViewModel {
//    override val UIState = MutableStateFlow(TopicScreenContracts.UIState())

    init {
        println("TopicScreenVM init")
    }

    override val searchQuery: MutableState<String> = mutableStateOf("")
    private val questions = ArrayList<CommonTopicItem>()

    private val commonTopicItems: ArrayList<CommonTopicResponse.Topic> = ArrayList()
    private val partTwoItems: ArrayList<PartTwoResponse.Topic> = ArrayList()

    override fun onEventDispatcher(intent: TopicScreenContracts.Intent): Job = intent {
        when (intent) {
            TopicScreenContracts.Intent.OnClickBack -> directions.goBack()
            TopicScreenContracts.Intent.SearchQuery -> reduce {
                state.copy(questions = questions.filter {
                    it.question.contains(
                        searchQuery.value,
                        ignoreCase = true
                    )
                })
            }

            is TopicScreenContracts.Intent.OnCLickTopic -> {
                when (state.section) {
                    Section.PART_TWO -> directions.goCueCardScreen(partTwoItems, intent.index)
                    else -> directions.goQuestionsScreen(state.section.title, commonTopicItems, intent.index)
                }
            }

        }
    }

    override fun init(section: Section): Job = intent {
        reduce { state.copy(isLoading = true, section = section) }

        when (section) {
            Section.PART_ONE -> getPartOneQuestions()
            Section.PART_TWO -> getPartTwoQuestions()
            Section.PART_THREE -> getPartThreeQuestions()
        }
    }

    private fun getPartOneQuestions() = intent {
        val showAnyWay = localStorage.getQuestionsVisibility()

        resultOf { repository.getPartOneQuestions() }
            .onSuccess { result ->
                var index = 0
                questions.addAll(result.filter { it.active || showAnyWay }.map { it.toCommonTopicItem(index++) })
                commonTopicItems.addAll(result)
                reduce { state.copy(isLoading = false, questions = questions) }
            }
            .onFailure { reduce { state.copy(isLoading = false, error = it.message) } }
    }

    private fun getPartTwoQuestions() = intent {
        val showAnyWay = localStorage.getQuestionsVisibility()

        resultOf { repository.getPartTwoQuestions() }
            .onSuccess { result ->
                var index = 0
                questions.addAll(result.filter { it.active || showAnyWay }.map { it.toCommonTopicItem(index++) })
                partTwoItems.addAll(result)
                reduce { state.copy(isLoading = false, questions = questions) }
            }
            .onFailure { reduce { state.copy(isLoading = false, error = it.message) } }
    }


    private fun getPartThreeQuestions() = intent {
        val showAnyWay = localStorage.getQuestionsVisibility()
        state
        resultOf { repository.getPartThreeQuestions() }
            .onSuccess { result ->
                var index = 0
                questions.addAll(result.filter { it.active || showAnyWay }.map { it.toCommonTopicItem(index++) })
                commonTopicItems.addAll(result)
                reduce { state.copy(isLoading = false, questions = questions) }
            }.onFailure { reduce { state.copy(isLoading = false, error = it.message) } }
    }

    override val container = container<TopicScreenContracts.UIState, Nothing>(TopicScreenContracts.UIState())
}