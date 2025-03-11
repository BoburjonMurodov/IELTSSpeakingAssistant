package com.boboor.speaking.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.boboor.speaking.ui.theme.AppTheme


/*
    Created by Boburjon Murodov 11/03/25 at 11:48
*/

object SettingsTab : Tab {

    override val options: TabOptions
        @Composable
        get() =
            TabOptions(
                index = 1u,
                title = "Settings",
                icon = rememberVectorPainter(Icons.Default.Settings)
            )

    @Composable
    override fun Content() {
        SettingsTabContent()
    }


    @Composable
    private fun SettingsTabContent() {
        AppTheme {
            Scaffold {

            }
        }
    }

}