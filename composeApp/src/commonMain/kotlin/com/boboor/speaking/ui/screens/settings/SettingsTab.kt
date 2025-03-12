package com.boboor.speaking.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
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


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SettingsTabContent() {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        AppTheme {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    LargeTopAppBar(
                        title = { Text("Settings") },
                        scrollBehavior = scrollBehavior
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(padding.calculateTopPadding()))
                    repeat(1) {
                        Text("Appearance", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))

                        ListItem(
                            headlineContent = { Text("Theme Color") },
                            supportingContent = { Text("selectedThemeColor") },
                            trailingContent = {
                                DropdownMenuComponent(
                                    options = listOf("green", "dark", "system"),
                                    selectedOption = "selectedThemeColor",
                                    onOptionSelected = { }
                                )
                            }
                        )

                        HorizontalDivider()

                        Text("Content Updates", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))

                        ListItem(
                            headlineContent = { Text("Update Frequency") },
                            supportingContent = { Text("updateFrequency") },
                            trailingContent = {
                                DropdownMenuComponent(
                                    options = listOf("every day", "every app opening", "never"),
                                    selectedOption = "updateFrequency",
                                    onOptionSelected = { }
                                )
                            }
                        )

                        HorizontalDivider()

                        Text("Preferences", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))

                        ListItem(
                            headlineContent = { Text("Show Hidden Questions") },
                            trailingContent = {
                                Switch(
                                    checked = true,
                                    onCheckedChange = {}
                                )
                            }
                        )

                        HorizontalDivider()

                        ListItem(
                            headlineContent = { Text("App Version") },
                            supportingContent = { Text("1.0.0") }
                        )
                    }
                    Spacer(Modifier.height(padding.calculateBottomPadding()))
                }
            }
        }
    }



}


@Composable
fun ListTile(
    title: String,
    content: @Composable () -> Unit,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .then(
                if (isFirst) Modifier.clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                else if (isLast) Modifier.clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                else Modifier
            ).background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.weight(1f))

        content.invoke()
    }
}


@Composable
fun DropdownMenuComponent(options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(selectedOption)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}