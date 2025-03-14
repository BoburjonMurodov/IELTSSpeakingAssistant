import com.android.utils.TraceUtils.simpleId
import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)

    //ROOM and KSP
//    alias(libs.plugins.kspCompose)
//    alias(libs.plugins.room)

}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            //KOIN DI
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            //KTOR
            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
//            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.coroutines.core)

            val voyagerVersion = "1.1.0-beta03"

            // VOYAGER
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion") // Navigator
            implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion") // Screen Model
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion") // Transitions
            implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion") // Koin integration
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion") // TabNavigator


            //KOIN DI
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.lifecycle.viewmodel)

            //TOAST
            implementation("network.chaintech:cmptoast:1.0.4")

            //KTOR
            implementation(libs.bundles.ktor)

            //SETTINGS LOCAL STORAGE
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")

            //MATERIAL COLOR GENERATOR
            implementation("com.materialkolor:material-kolor:2.0.2")

            //MATERIAL 3
//            implementation("org.jetbrains.compose.material3:material3")
            implementation("org.jetbrains.compose.material3:material3:1.7.3")


            //SHIMMER
            implementation("com.valentinilk.shimmer:compose-shimmer:1.3.2")

            //HAZE FOR BLUR
            implementation("dev.chrisbanes.haze:haze:1.3.1")


            //TTS
            val ttsVersion = "3.0.0-alpha.2"
            implementation("nl.marc-apps:tts:$ttsVersion")
            implementation("nl.marc-apps:tts-compose:$ttsVersion")

            //HTML PARSER
            implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-rc11")

        }


        nativeMain.dependencies {
            //KTOR
            implementation(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            //KTOR
            implementation(libs.ktor.client.okhttp)

            //TTS
            implementation("net.java.dev.jna:jna:5.12.1")
        }
    }
}

android {
    namespace = "com.boboor.speaking"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.boboor.speaking"
        minSdk = 25
        ndkVersion = "26.1.10909125"
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/androidMain/cpp/CMakeLists.txt")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation(libs.androidx.ui.text.google.fonts)
}


//room {
//    schemaDirectory("$projectDir/schemas")
//}
//
//dependencies {
//    implementation(libs.androidx.sqlite.bundled.android)
//    add("kspCommonMainMetadata", libs.room.compiler)
//}
//
//tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
//    if (name != "kspCommonMainKotlinMetadata" ) {
//        dependsOn("kspCommonMainKotlinMetadata")
//    }
//}



compose.desktop {
    application {
        mainClass = "com.boboor.speaking.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.boboor.speaking"
            packageVersion = "1.0.0"

        }
    }
}
