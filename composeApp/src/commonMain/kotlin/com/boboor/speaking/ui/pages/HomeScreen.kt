package com.boboor.speaking.ui.pages

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.ScreenTransition
import com.boboor.speaking.ui.pages.tabs.main.MainTab
import com.boboor.speaking.ui.pages.tabs.settings.SettingsTab
import com.boboor.speaking.ui.theme.AppTheme
import kotlinx.serialization.Transient


/*
    Created by Boburjon Murodov 09/03/25 at 21:29
*/

@OptIn(ExperimentalVoyagerApi::class)
class HomeScreen : Screen, ScreenTransition {

    @Transient
    private val fadeTransition = CustomFadeTransition()

    override fun enter(lastEvent: StackEvent) = fadeTransition.enter(lastEvent)
    override fun exit(lastEvent: StackEvent)  = fadeTransition.exit(lastEvent)


    @Composable
    override fun Content() {
        val tabs = listOf(MainTab, SettingsTab)

        TabNavigator(
            tab = MainTab,
            tabDisposable = { TabDisposable(it, tabs = tabs) }
        ) {
            Scaffold(
                bottomBar = {
                    AppTheme { BottomAppBar { tabs.forEach { MaterialNavigationBarItem(it) } } }
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(bottom = it.calculateBottomPadding())
                ) {
                    CurrentTab()
                }
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
                tint = if (tabNavigator.current == tab) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}


@OptIn(ExperimentalVoyagerApi::class)
class CustomFadeTransition : ScreenTransition {

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return fadeIn(tween(300))
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return fadeOut(tween(300))
    }
}
