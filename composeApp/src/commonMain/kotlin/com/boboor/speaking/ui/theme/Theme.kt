package com.boboor.speaking.ui.theme

import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.boboor.speaking.TimeUtil
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SettingsListener
import com.russhwolf.settings.get
import ieltsspeakingassistant.composeapp.generated.resources.Res
import ieltsspeakingassistant.composeapp.generated.resources.mont_bold
import ieltsspeakingassistant.composeapp.generated.resources.mont_medium
import ieltsspeakingassistant.composeapp.generated.resources.mont_regular
import ieltsspeakingassistant.composeapp.generated.resources.mont_semibold
import org.jetbrains.compose.resources.Font
import kotlin.jvm.JvmInline
import kotlin.math.log


/*
    Created by Boburjon Murodov 19/12/24 at 23:33
*/

//private val DarkColorScheme = darkColors(
//    primary = Color(0xFFBB86FC),
//    primaryVariant = Color(0xFF3700B3),
//    secondary = Color(0xFF03DAC5),
//    surface = Color.Black,
//
//)
//
//private val LightColorScheme = darkColors(
//    primary = Color(0xFF6200EE),
//    primaryVariant = Color(0xFF3700B3),
//    secondary = Color(0xFF03DAC5),
//    surface = Color.White
//)

@Composable
fun MontFontFamily() = FontFamily(
    Font(Res.font.mont_semibold, FontWeight.SemiBold),
    Font(Res.font.mont_bold, FontWeight.Bold),
    Font(Res.font.mont_medium, FontWeight.Medium),
    Font(Res.font.mont_regular, FontWeight.Normal),
)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = MaterialTheme.typography.copy(
        displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = MontFontFamily()),
        displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = MontFontFamily()),
        displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = MontFontFamily()),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = MontFontFamily()),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = MontFontFamily()),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = MontFontFamily()),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = MontFontFamily()),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = MontFontFamily()),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = MontFontFamily()),
        titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = MontFontFamily()),
        titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = MontFontFamily()),
        titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = MontFontFamily()),
        labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = MontFontFamily()),
        labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = MontFontFamily()),
        labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = MontFontFamily()),
    )

    val colorCode = remember { mutableStateOf(0L) }
    val seedColor = remember(colorCode.value) { mutableStateOf(getColor()) }

    DisposableEffect(Unit) {
        val listener = colorSettings.addLongListener("color", 0xff7b580c) { newColor ->
            seedColor.value = Color(newColor)
            colorCode.value = newColor
        }

        onDispose {
            listener.deactivate()
        }
    }


    DynamicMaterialTheme(
        seedColor = seedColor.value,
        animate = true,
        animationSpec = tween(1000),
        content = content,
        shapes = MaterialTheme.shapes,
        typography = typography,
        withAmoled = true,
        style = PaletteStyle.Content
    )
}

private val colorSettings = MakeObservableSettings(Settings()) //Settings() as ObservableSettings

fun getColor(): Color {
    val colorCode = colorSettings.getLong("color", 0xff7b580c)
    println("colorCode from getColor = $colorCode")
    return Color(colorCode.toULong())
}


fun setColor(color: Color) {
    println("color = $color")
    val colorCode = color.value
    colorSettings.putLong("color", colorCode.toLong())
}


private class MakeObservableSettings(
    private val delegate: Settings,
) : Settings by delegate, ObservableSettings {

    private val listenerMap = mutableMapOf<String, MutableSet<() -> Unit>>()

    override fun remove(key: String) {
        delegate.remove(key)
        invokeListeners(key)
    }

    override fun clear() {
        delegate.clear()
        invokeAllListeners()
    }

    override fun putInt(key: String, value: Int) {
        delegate.putInt(key, value)
        invokeListeners(key)
    }

    override fun putLong(key: String, value: Long) {
        delegate.putLong(key, value)
        invokeListeners(key)
    }

    override fun putString(key: String, value: String) {
        delegate.putString(key, value)
        invokeListeners(key)
    }

    override fun putFloat(key: String, value: Float) {
        delegate.putFloat(key, value)
        invokeListeners(key)
    }

    override fun putDouble(key: String, value: Double) {
        delegate.putDouble(key, value)
        invokeListeners(key)
    }

    override fun putBoolean(key: String, value: Boolean) {
        delegate.putBoolean(key, value)
        invokeListeners(key)
    }

    override fun addIntListener(
        key: String,
        defaultValue: Int,
        callback: (Int) -> Unit
    ): SettingsListener = addListener<Int>(key) {
        callback(getInt(key, defaultValue))
    }


    override fun addLongListener(
        key: String,
        defaultValue: Long,
        callback: (Long) -> Unit
    ): SettingsListener = addListener<Long>(key) {
        callback(getLong(key, defaultValue))
    }

    override fun addStringListener(
        key: String,
        defaultValue: String,
        callback: (String) -> Unit
    ): SettingsListener = addListener<String>(key) {
        callback(getString(key, defaultValue))
    }

    override fun addFloatListener(
        key: String,
        defaultValue: Float,
        callback: (Float) -> Unit
    ): SettingsListener = addListener<Float>(key) {
        callback(getFloat(key, defaultValue))
    }

    override fun addDoubleListener(
        key: String,
        defaultValue: Double,
        callback: (Double) -> Unit
    ): SettingsListener = addListener<Double>(key) {
        callback(getDouble(key, defaultValue))
    }

    override fun addBooleanListener(
        key: String,
        defaultValue: Boolean,
        callback: (Boolean) -> Unit
    ): SettingsListener = addListener<Boolean>(key) {
        callback(getBoolean(key, defaultValue))
    }

    override fun addIntOrNullListener(
        key: String, callback: (Int?) -> Unit
    ): SettingsListener = addListener<Int>(key) {
        callback(getIntOrNull(key))
    }

    override fun addLongOrNullListener(
        key: String,
        callback: (Long?) -> Unit
    ): SettingsListener = addListener<Long>(key) {
        callback(getLongOrNull(key))
    }

    override fun addStringOrNullListener(
        key: String,
        callback: (String?) -> Unit
    ): SettingsListener = addListener<String>(key) {
        callback(getStringOrNull(key))
    }

    override fun addFloatOrNullListener(
        key: String,
        callback: (Float?) -> Unit
    ): SettingsListener = addListener<Float>(key) {
        callback(getFloatOrNull(key))
    }

    override fun addDoubleOrNullListener(
        key: String,
        callback: (Double?) -> Unit
    ): SettingsListener = addListener<Double>(key) {
        callback(getDoubleOrNull(key))
    }

    override fun addBooleanOrNullListener(
        key: String,
        callback: (Boolean?) -> Unit
    ): SettingsListener = addListener<Boolean>(key) {
        callback(getBooleanOrNull(key))
    }

    private inline fun <reified T> addListener(
        key: String,
        noinline callback: () -> Unit
    ): SettingsListener {
        var prev: T? = delegate[key]

        val listener = {
            val current: T? = delegate[key]
            if (prev != current) {
                callback()
                prev = current
            }
        }

        val listeners = listenerMap.getOrPut(key) { mutableSetOf() }
        listeners += listener

        return Listener {
            removeListener(key, listener)
        }
    }

    private fun removeListener(key: String, listener: () -> Unit) {
        listenerMap[key]?.also {
            it -= listener
        }
    }

    private fun invokeListeners(key: String) {
        listenerMap[key]?.forEach { callback ->
            callback()
        }
    }

    private fun invokeAllListeners() {
        listenerMap.forEach { entry ->
            entry.value.forEach { callback ->
                callback()
            }
        }
    }

}


@JvmInline
private value class Listener(
    private val removeListener: () -> Unit
) : SettingsListener {

    override fun deactivate(): Unit = removeListener()
}

