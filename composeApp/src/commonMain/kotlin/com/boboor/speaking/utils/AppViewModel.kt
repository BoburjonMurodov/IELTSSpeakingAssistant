package com.boboor.speaking.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.Syntax

//import org.orbitmvi.orbit.ContainerHost
//import org.orbitmvi.orbit.SettingsBuilder
//import org.orbitmvi.orbit.container
//import org.orbitmvi.orbit.syntax.Syntax


/*
    Created by Boburjon Murodov 20/12/24 at 18:49
*/


interface AppViewModel<STATE : Any, SIDE_EFFECT : Any> : ContainerHost<STATE, SIDE_EFFECT>, ScreenModel {
    fun <STATE : Any, SIDE_EFFECT : Any> container(
        initialState: STATE,
        buildSettings: SettingsBuilder.() -> Unit = {},
        onCreate: Syntax<STATE, SIDE_EFFECT>.() -> Unit = {},
    ) = screenModelScope.container<STATE, SIDE_EFFECT>(initialState, buildSettings, onCreate)
}

@Composable
fun <STATE : Any, SIDE_EFFECT : Any> AppViewModel<STATE, SIDE_EFFECT>.collectAsState() = container.stateFlow.collectAsState()


//interface AppViewModel<STATE : Any> : ScreenModel {
//    val UIState: MutableStateFlow<STATE>
//    private val reducer: AppSyntax<STATE>
//        get() = AppSyntax(UIState)
//
//    fun intent(dispatcher: CoroutineDispatcher = Dispatchers.Main, action: suspend AppSyntax<STATE>.() -> Unit): Job =
//        screenModelScope.launch(dispatcher + SupervisorJob()) {
//            action.invoke(reducer)
//        }
//
//    @Composable
//    fun collectAsState() = UIState.collectAsState()
//}
//
//
//class AppSyntax<STATE>(val state: MutableStateFlow<STATE>) {
//    suspend fun reduce(action: suspend () -> STATE) {
//        state.update { action.invoke() }
//    }
//}
//
//interface Container<STATE : Any, SIDE_EFFECT : Any>{
//    val stateFlow: MutableStateFlow<STATE>
//    val sideEffect: MutableStateFlow<SIDE_EFFECT>
//}
