package com.boboor.speaking.ui.pages.screens.commonQuestions

import com.boboor.speaking.data.repository.AppRepository
import com.boboor.speaking.ui.pages.screens.commonQuestions.CommonQuestionContracts.Intent
import com.boboor.speaking.utils.enums.Section
import kotlinx.coroutines.Job


/*
    Created by Boburjon Murodov 18/03/25 at 21:02
*/

class CommonQuestionVM(
    private val repository: AppRepository
) : CommonQuestionContracts.ViewModel {
    override fun onEventDispatcher(intent: Intent): Job = intent {
        when(intent){
            is Intent.Init -> {
//                val questions = if (intent.section == Section.PART_ONE) {
//                    repository.updateOneQuestions()
//                }else repository.updateThreeQuestions()
            }

        }
    }

    override val container = container<CommonQuestionContracts.UIState, Nothing>(CommonQuestionContracts.UIState())
}