import org.gradle.kotlin.dsl.implementation
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)

//    id("androidx.room")
//    id("com.google.devtools.ksp")
//    alias(libs.plugins.room)
//    alias(libs.plugins.kspCompose)

    id("app.cash.sqldelight") version "2.0.2"
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

//            implementation(libs.android.database.sqlcipher)
//            implementation("net.zetetic:android-database-sqlcipher:4.5.0")

            //SQL DELIGHT
//            implementation("app.cash.sqldelight:android-driver:2.0.2")
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


            // VOYAGER
            implementation(libs.voyager.navigator) // Navigator
            implementation(libs.voyager.screenmodel) // Screen Model
            implementation(libs.voyager.transitions) // Transitions
            implementation(libs.voyager.koin) // Koin integration
            implementation(libs.voyager.tab.navigator) // TabNavigator


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

            //ORBIT MVI
            implementation("org.orbit-mvi:orbit-core:9.0.0")

            //ROOM
//            implementation(libs.room.runtime)
//            implementation(libs.sqlite.bundled)
        }


        nativeMain.dependencies {
            //KTOR
            implementation(libs.ktor.client.darwin)

            //SQL DELIGHT
//            implementation("app.cash.sqldelight:native-driver:2.0.2")
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            //KTOR
            implementation(libs.ktor.client.okhttp)

            //TTS
            implementation("net.java.dev.jna:jna:5.12.1")

            //SQL DELIGHT
//            implementation ("app.cash.sqldelight:sqlite-driver:2.0.2")
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
        debug {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

//room {
//    schemaDirectory("$projectDir/schemas")
//}
//
//dependencies {
//    ksp(libs.room.compiler)
//}
//
//ksp{
//    arg("room.schemaLocation", "$projectDir/schemas")
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


sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.boboor")
        }
    }
}