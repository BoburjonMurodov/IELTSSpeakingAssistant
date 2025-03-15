package com.boboor.speaking.ui.pages.screens.license

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen


/*
    Created by Boburjon Murodov 15/03/25 at 11:51
*/

class LicenseScreen : Screen {

    @Composable
    override fun Content() {

    }

    @Composable
    private fun LicenseScreenContent() {

    }
}

data class LicenseData(
    val name: String,
    val link: String
)

private val licenseList = listOf(
    LicenseData("Compose multiplatform", "https://github.com/JetBrains/compose-multiplatform/blob/master/LICENSE.txt"),
    LicenseData("Voyager", " https://github.com/adrielcafe/voyager/blob/main/LICENSE.md"),
    LicenseData("Koin", " https://github.com/InsertKoinIO/koin/blob/main/LICENSE"),
    LicenseData("Toast", "https://github.com/Chaintech-Network/CMPToast/blob/main/LICENSE.txt"),
    LicenseData("Ktor", "https://github.com/ktorio/ktor/blob/main/LICENSE"),
    LicenseData("Multiplatform Settings", "https://github.com/russhwolf/multiplatform-settings/blob/main/LICENSE.txt"),
    LicenseData("Shimmer", "https://github.com/valentinilk/compose-shimmer/blob/master/LICENSE.md"),
    LicenseData("Haze", "https://github.com/chrisbanes/haze/blob/main/LICENSE"),
    LicenseData("Text to Speech (TTS)", "https://github.com/Marc-JB/TextToSpeechKt/blob/main/LICENSE"),
    LicenseData("Compose RichEditor", "https://github.com/MohamedRejeb/compose-rich-editor/blob/main/LICENSE"),
    LicenseData("Kotlinx.serialization", "https://github.com/Kotlin/kotlinx.serialization/blob/master/LICENSE.txt"),
    LicenseData("Kotlinx.coroutines", "https://github.com/Kotlin/kotlinx.coroutines/blob/master/LICENSE.txt"),
    LicenseData("ksp", "https://github.com/google/ksp/blob/main/LICENSE"),
    LicenseData("Androidx", "https://github.com/androidx/androidx/blob/androidx-main/LICENSE.txt"),
    LicenseData("okhttp", "https://github.com/square/okhttp/blob/master/LICENSE.txt"),
    LicenseData("Sofia Pro", "https://github.com/William-Jung/WeJ/blob/master/LICENSE"),

    )