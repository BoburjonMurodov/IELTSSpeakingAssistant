package com.boboor.speaking.ui.pages.screens.topic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.boboor.speaking.data.local.LocalStorage
import com.boboor.speaking.data.remote.ApiService
import com.boboor.speaking.data.remote.models.CommonTopicResponse
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
    private val localStorage: LocalStorage
) : TopicScreenContracts.ViewModel {
    override val UIState = MutableStateFlow(TopicScreenContracts.UIState())

    override val searchQuery: MutableState<String> = mutableStateOf("")
    private val questions = ArrayList<CommonTopicResponse.Topic>()

//    fun intent(action: suspend () -> Unit): Job =
//        screenModelScope.launch {
//            action.invoke();
//        }


    override fun onEventDispatcher(intent: TopicScreenContracts.Intent): Job = intent {
        when (intent) {
            TopicScreenContracts.Intent.OnClickBack -> directions.goBack()
            TopicScreenContracts.Intent.SearchQuery ->


                UIState.update {
                    it.copy(questions = questions.filter {
                        it.name.contains(
                            searchQuery.value,
                            ignoreCase = true
                        )
                    })
                }

            is TopicScreenContracts.Intent.OnClickTopic -> directions.goQuestionsScreen(
                intent.title,
                topics = intent.topics,
                topicIndex = intent.topicIndex
            )

        }
    }

    override fun init(section: Section): Job = intent {
        UIState.update { it.copy(isLoading = true, section = section) }
//        delay(1_000)

        when (section) {
            Section.PART_ONE -> getPartOneQuestions()
            Section.PART_TWO -> {}
            Section.PART_THREE -> getPartThreeQuestions()
        }
    }

    private fun getPartOneQuestions() = intent(Dispatchers.IO) {
        val partOneQuestions = localStorage.getPartOne()
        val showAnyWay = localStorage.getQuestionsVisibility()

        if (partOneQuestions == null) {
            println("getPartOneQuestions from net")
            resultOf { apiService.getPartOneQuestions() }
                .onSuccess { result ->
                    result.content.forEach { if (it.value.active || showAnyWay) questions.add(it.value) }
                    localStorage.addPartOne(result)
                    UIState.update { it.copy(isLoading = false, questions = questions) }
                }.onFailure {
                    println("ERROR ${it.message}")
                    UIState.update { it.copy(isLoading = false, error = it.error) }
                }
        } else {
            println("getPartOneQuestions from local")
            partOneQuestions.content.forEach { if (it.value.active || showAnyWay) questions.add(it.value) }
            UIState.update { it.copy(isLoading = false, questions = questions) }
        }
    }


    private fun getPartThreeQuestions() = intent(Dispatchers.IO) {
        val partThreeQuestions = localStorage.getPartThree()
        val showAnyWay = localStorage.getQuestionsVisibility()
        if (partThreeQuestions == null) {
            resultOf { apiService.getPartThreeQuestions() }
                .onSuccess { result ->
                    result.content.forEach { if (it.value.active || showAnyWay) questions.add(it.value) }
                    localStorage.addPartThree(result)
                    UIState.update { it.copy(isLoading = false, questions = questions) }
                }.onFailure {
                    println("ERROR ${it.message}")
                    UIState.update { it.copy(isLoading = false, error = it.error) }
                }
        } else {
            partThreeQuestions.content.forEach { if (it.value.active || showAnyWay) questions.add(it.value) }
            UIState.update { it.copy(isLoading = false, questions = questions) }
        }
    }

//    override val container = container<TopicScreenContracts.UIState, Nothing>(TopicScreenContracts.UIState())
}