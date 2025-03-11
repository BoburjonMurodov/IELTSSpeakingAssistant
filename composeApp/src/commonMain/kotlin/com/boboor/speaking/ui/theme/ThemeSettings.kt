package com.boboor.speaking.ui.theme

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SettingsListener
import com.russhwolf.settings.get
import kotlin.jvm.JvmInline


/*
    Created by Boburjon Murodov 11/03/25 at 10:55
*/



class MakeObservableSettings(
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
