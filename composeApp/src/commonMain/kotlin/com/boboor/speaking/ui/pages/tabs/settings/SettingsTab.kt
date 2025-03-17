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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.boboor.speaking.ui.components.DropdownMenuComponent
import com.boboor.speaking.ui.pages.tabs.settings.bottomsheet.ChangeThemeBottomSheet
import com.boboor.speaking.ui.theme.AppTheme
import com.boboor.speaking.ui.theme.FontDimension
import com.boboor.speaking.ui.theme.getFontDimension
import com.boboor.speaking.ui.theme.setFontDimension
import com.boboor.speaking.utils.collectAsState
import com.boboor.speaking.utils.debounceClickable
import com.boboor.speaking.utils.enums.EVERY_DAY
import com.boboor.speaking.utils.enums.NEVER
import com.boboor.speaking.utils.enums.UpdateFrequency
import com.boboor.speaking.utils.gradient
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
        val uriHandler = LocalUriHandler.current

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
                        supportingContent = { Text("How often to cache updates") },
                        trailingContent = {
                            DropdownMenuComponent(
                                options = UpdateFrequency.entries.map { it.title },
                                selectedOption = state.value.selectedUpdateFrequency.title,
                                onOptionSelected = {
                                    val value = when (it) {
                                        EVERY_DAY -> UpdateFrequency.EVERY_DAY
                                        NEVER -> UpdateFrequency.NEVER
                                        else -> UpdateFrequency.EVERY_APP_OPENING
                                    }
                                    onEventDispatcher(SettingsContracts.Intent.OnSelectedUpdateFrequency(value))
                                }
                            )
                        }
                    )

                    ListItem(
                        headlineContent = { Text("Show Hidden Questions") },
                        supportingContent = { Text("Outdated questions might be hidden") },
                        trailingContent = {
                            Switch(
                                checked = state.value.showHiddenQuestions,
                                onCheckedChange = {
                                    onEventDispatcher(SettingsContracts.Intent.OnClickShowHideQuestions(it))
                                }
                            )
                        }
                    )

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
                            onEventDispatcher.invoke(SettingsContracts.Intent.GoLicenseScreen)
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

