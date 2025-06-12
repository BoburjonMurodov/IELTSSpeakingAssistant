plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    val room_version = "2.7.0"
    id("androidx.room") version "$room_version" apply false
    id("com.google.devtools.ksp") version "2.1.20-2.0.0" apply false
    id("app.cash.sqldelight") version "2.0.2" apply false
}