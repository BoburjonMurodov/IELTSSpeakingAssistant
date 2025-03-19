package com.boboor.speaking.ui.pages.screens.license

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.boboor.speaking.ui.components.AppBar
import com.boboor.speaking.ui.components.SwipeToDismissPage
import com.boboor.speaking.utils.debounceClickable


/*
    Created by Boburjon Murodov 15/03/25 at 11:51
*/

class LicenseScreen : Screen {

    @Composable
    override fun Content() {
        SwipeToDismissPage {
            LicenseScreenContent()
        }
    }

    @Composable
    private fun LicenseScreenContent() {
        val navigator = LocalNavigator.currentOrThrow
        val uriHandler = LocalUriHandler.current
        Scaffold(
            topBar = {
                AppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow),
                    title = "Licenses",
                    showSearch = false,
                    isSearchEnabled = false,
                    onClickBack = {
                        navigator.pop()
                    },
                )
            }
        ) { innerPadding ->
            LazyColumn(
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 16.dp,
                )
            ) {
                items(licenseList) {
                    ListItem(
                        headlineContent = {
                            Text(it.name)
                        },
                        modifier = Modifier.debounceClickable {
                            uriHandler.openUri(it.link.trim())
                        }
                    )
                }
            }
        }
    }
}

data class LicenseData(
    val name: String,
    val link: String
)

private val licenseList = listOf(
    LicenseData("Compose multiplatform", "https://github.com/JetBrains/compose-multiplatform/blob/master/LICENSE.txt"),
    LicenseData("Voyager", "https://github.com/adrielcafe/voyager/blob/main/LICENSE.md"),
    LicenseData("Orbit MVI", "https://github.com/orbit-mvi/orbit-mvi/blob/main/LICENSE.md"),
    LicenseData("Koin", " https://github.com/InsertKoinIO/koin/blob/main/LICENSE"),
    LicenseData("Toast", "https://github.com/Chaintech-Network/CMPToast/blob/main/LICENSE.txt"),
    LicenseData("Ktor", "https://github.com/ktorio/ktor/blob/main/LICENSE"),
    LicenseData("Multiplatform Settings", "https://github.com/russhwolf/multiplatform-settings/blob/main/LICENSE.txt"),
    LicenseData("Shimmer", "https://github.com/valentinilk/compose-shimmer/blob/master/LICENSE.md"),
    LicenseData("Haze", "https://github.com/chrisbanes/haze/blob/main/LICENSE"),
    LicenseData("Text to Speech (TTS)", "https://github.com/Marc-JB/TextToSpeechKt/blob/main/LICENSE"),
    LicenseData("Compose RichEditor", "https://github.com/MohamedRejeb/compose-rich-editor/blob/main/LICENSE"),
    LicenseData("kotlinx.serialization", "https://github.com/Kotlin/kotlinx.serialization/blob/master/LICENSE.txt"),
    LicenseData("kotlinx.coroutines", "https://github.com/Kotlin/kotlinx.coroutines/blob/master/LICENSE.txt"),
    LicenseData("ksp", "https://github.com/google/ksp/blob/main/LICENSE"),
    LicenseData("androidx", "https://github.com/androidx/androidx/blob/androidx-main/LICENSE.txt"),
    LicenseData("okhttp", "https://github.com/square/okhttp/blob/master/LICENSE.txt"),
    LicenseData("Sofia Pro font", "https://github.com/William-Jung/WeJ/blob/master/LICENSE"),

    )