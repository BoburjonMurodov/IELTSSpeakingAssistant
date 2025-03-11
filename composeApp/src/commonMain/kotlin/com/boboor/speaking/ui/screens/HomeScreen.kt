package com.boboor.speaking.ui.screens

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.boboor.speaking.ui.screens.main.MainScreen
import com.boboor.speaking.ui.theme.AppTheme


/*
    Created by Boburjon Murodov 09/03/25 at 21:29
*/

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        TabNavigator(
            tab = MainScreen,
            tabDisposable = {
                TabDisposable(it, tabs = listOf(MainScreen))
            }
        ) {
            Scaffold(
                bottomBar = {
                    AppTheme {
                        val tabs = listOf(MainScreen)
                        val tabNavigator = LocalTabNavigator.current

                        BottomAppBar {
                            tabs.forEach {
                                NavigationBarItem(
                                    selected = tabNavigator.current == it,
                                    onClick = { tabNavigator.current = it },
                                    label = { Text(it.options.title) },
                                    icon = { Icon(painter = it.options.icon!!, contentDescription = it.options.title) }
                                )
                            }
                        }
                    }
                }
            ) {
                CurrentTab()
            }
        }
    }
}