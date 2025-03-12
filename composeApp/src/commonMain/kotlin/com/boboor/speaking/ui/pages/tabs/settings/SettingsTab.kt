package com.boboor.speaking.ui.pages.tabs.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.boboor.speaking.ui.pages.tabs.settings.bottomsheet.ChangeThemeBottomSheet
import com.boboor.speaking.ui.theme.AppTheme
import com.boboor.speaking.ui.theme.FontDimension
import com.boboor.speaking.ui.theme.getFontDimension
import com.boboor.speaking.ui.theme.setFontDimension
import com.boboor.speaking.utils.darken
import com.boboor.speaking.utils.debounceClickable
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource


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
        val viewModel = koinScreenModel<SettingsContracts.ViewModel>()
        val state = viewModel.collectAsState()
        SettingsTabContent(state, viewModel::onEventDispatcher)
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SettingsTabContent(
        state: State<SettingsContracts.UIState>,
        onEventDispatcher: (SettingsContracts.Intent) -> Unit = {}
    ) {
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

                    Text("Appearance", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))

                    ListItem(
                        modifier = Modifier.debounceClickable {
                            onEventDispatcher(SettingsContracts.Intent.OpenChangeThemeBottomSheet)
                        },
                        headlineContent = { Text("Theme Color") },
                        trailingContent = {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(state.value.selectedThemeColor.gradient())
                            )
                        }
                    )

                    ListItem(
                        headlineContent = { Text("Font Size") },
                        supportingContent = { Text(getFontDimension().name) },
                        trailingContent = {
                            DropdownMenuComponent(
                                options = FontDimension.entries.map { it.name },
                                selectedOption = getFontDimension().name,
                                onOptionSelected = {
                                    setFontDimension(FontDimension.valueOf(it))
                                }
                            )
                        }
                    )

                    HorizontalDivider()

                    Text("Content Settings", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))

                    ListItem(
                        headlineContent = { Text("Update Frequency") },
                        supportingContent = { Text("Every day") },
                        trailingContent = {
                            DropdownMenuComponent(
                                options = listOf("Every day", "Every app opening", "Never"),
                                selectedOption = "Every day",
                                onOptionSelected = { }
                            )
                        }
                    )


                    ListItem(
                        headlineContent = { Text("Show Hidden Questions") },
                        trailingContent = {
                            Switch(
                                checked = true,
                                onCheckedChange = {

                                }
                            )
                        }
                    )

                    val uriHandler = LocalUriHandler.current
                    HorizontalDivider()

                    ListItem(
                        headlineContent = { Text("Our Telegram Channel") },
                        supportingContent = { Text("Join our channel for news and updates") },
                        trailingContent = {
                            Icon(
                                modifier = Modifier.graphicsLayer {
                                    rotationZ = 180f
                                },
                                painter = painterResource(Res.drawable.ic_back),
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.debounceClickable {
                            uriHandler.openUri("https://t.me/pwn17wnd")
                        }
                    )

                    HorizontalDivider()

                    ListItem(
                        headlineContent = { Text("Licenses") },
                        supportingContent = { Text("Third-party licenses") },
                        trailingContent = {
                            Icon(
                                modifier = Modifier.graphicsLayer {
                                    rotationZ = 180f
                                },
                                painter = painterResource(Res.drawable.ic_back),
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.debounceClickable {
                            uriHandler.openUri("https://github.com/BoburjonMurodov")
                        }
                    )

                    HorizontalDivider()

                    ListItem(
                        headlineContent = {
                            Text(
                                text = "Clear Cache",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "app will be restarted",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        trailingContent = {
                            Icon(
                                Icons.Default.Clear, contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        },
                        modifier = Modifier.debounceClickable {
                            onEventDispatcher.invoke(SettingsContracts.Intent.OnClickClearCache)
                        }
                    )

                    HorizontalDivider()

                    ListItem(
                        headlineContent = { Text("App Version") },
                        supportingContent = { Text("1.0.0") },
                    )

                    Spacer(Modifier.height(padding.calculateBottomPadding()))
                }

                if (state.value.isChangeThemeBottomSheetOpen) {
                    ChangeThemeBottomSheet(
                        onClick = {
                            onEventDispatcher(SettingsContracts.Intent.ChangeThemeColor(it))
                            onEventDispatcher(SettingsContracts.Intent.DismissChangeThemeBottomSheet)
                        },
                        onDismiss = {
                            onEventDispatcher(SettingsContracts.Intent.DismissChangeThemeBottomSheet)
                        })
                }
            }
        }
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

val seedColors = listOf(
    Color(0xFFF44336),
    Color(0xFFE91E63),
    Color(0xFF9C27B0),
    Color(0xFF673AB7),
    Color(0xFF3F51B5),
    Color(0xFF2196F3),
    Color(0xFF03A9F4),
    Color(0xFF00BCD4),
    Color(0xFF009688),
    Color(0xFF4CAF50),
    Color(0xFF8BC34A),
    Color(0xFFCDDC39),
    Color(0xFFFFEB3B),
    Color(0xFFFFC107),
    Color(0xFFFF9800),
    Color(0xFFFF5722),
    Color(0xFF795548),
    Color(0xFF9E9E9E),
    Color(0xFF607D8B),
    Color(0xFF000000),
    Color(0xFFFFFFFF)
)

fun Color.gradient(): Brush {
    return Brush.linearGradient(
        colors = listOf(
            this,
            this.copy(alpha = 0.8f).darken(0.5f)
        )
    )
}