package com.boboor.speaking.utils

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.Syntax


/*
    Created by Boburjon Murodov 20/12/24 at 18:49
*/



interface AppViewModel <STATE: Any, SIDE_EFFECT : Any> : ContainerHost<STATE, SIDE_EFFECT>, ScreenModel {
    fun <STATE: Any, SIDE_EFFECT : Any> container(
        initialState: STATE,
        buildSettings: SettingsBuilder.() -> Unit = {},
        onCreate:  Syntax<STATE, SIDE_EFFECT>.() -> Unit = {},
    ) = screenModelScope.container<STATE, SIDE_EFFECT>(initialState, buildSettings, onCreate)
}
