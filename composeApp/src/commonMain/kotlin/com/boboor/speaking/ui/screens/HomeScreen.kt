package com.boboor.speaking.ui.screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.boboor.speaking.ui.screens.main.MainTab
import com.boboor.speaking.ui.screens.settings.SettingsTab
import com.boboor.speaking.ui.theme.AppTheme


/*
    Created by Boburjon Murodov 09/03/25 at 21:29
*/

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val tabs = listOf(MainTab, SettingsTab)

        TabNavigator(
            tab = MainTab,
            tabDisposable = {
                TabDisposable(it, tabs = tabs)
            }
        ) {
            Scaffold(
                bottomBar = {
                    AppTheme {
//                        val tabs = listOf(MainTab)
//                        val tabNavigator = LocalTabNavigator.current

                        BottomAppBar {
                            tabs.forEach {
//                                NavigationBarItem(
//                                    selected = tabNavigator.current == it,
//                                    onClick = { tabNavigator.current = it },
//                                    label = { Text(it.options.title) },
//                                    icon = { Icon(painter = it.options.icon!!, contentDescription = it.options.title) }
//                                )

                                MaterialNavigationBarItem(it)
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


@Composable
private fun RowScope.MaterialNavigationBarItem(
    tab: Tab
) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        label = {
            Text(
                tab.options.title,
                style = MaterialTheme.typography.labelMedium,
                color = if (tabNavigator.current == tab) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        icon = {
            Icon(
                painter = tab.options.icon!!,
                contentDescription = tab.options.title,
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
    )
}
