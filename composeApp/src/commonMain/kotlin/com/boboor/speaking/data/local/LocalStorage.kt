package com.boboor.speaking.data.local

import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.experimental.xor
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.random.Random


/*
    Created by Boburjon Murodov 20/02/25 at 13:59
*/

class LocalStorage {
    private val settings = Settings()
    val PART_ONE = "SPEAKING_ONE".encrypt(getKey())
    val PART_TWO = "SECTION_TWO".encrypt(getKey())
    val PART_THREE = "PART_THREE".encrypt(getKey())

    private lateinit var partOneResponse: CommonTopicResponse.Response
    private lateinit var partThreeResponse: CommonTopicResponse.Response


    private companion object {
        const val KEY = "A&Oi%s129#ANmlsd!@fg12)asmn=="
    }

    init {
        val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

        coroutineScope.launch {
            val partOne = getPartOne()
            partOne?.let { partOneResponse = it }

            val partThree = getPartThree()
            partThree?.let { partThreeResponse = it }

            this.cancel()
        }
    }

    private fun getRandomString(length: Int = Random.nextInt(12, 55)): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    private fun getKey(): String {
        val key = settings.getString(KEY, "")
        if (key.isEmpty()) {
            settings.putString(KEY, getRandomString())
        }
        return settings.getString(KEY, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
    }

    suspend fun addPartOne(value: CommonTopicResponse.Response) = withContext(Dispatchers.IO) {
        val json = Json.encodeToString(value).encryptWithKey(getKey())
        settings.putString(PART_ONE, json)
    }

    suspend fun getPartOne(): CommonTopicResponse.Response? = withContext(Dispatchers.IO) {
        if (::partOneResponse.isInitialized) return@withContext partOneResponse

        val json = settings.getString(PART_ONE, "").decryptWithKey(getKey())
        if (json.isEmpty()) return@withContext null
        return@withContext try {
            Json.decodeFromString(json)
        } catch (e: Exception) {
            e.printStackTrace()
            println("getPartOne error ${e.message}")
            null
        }
    }

    suspend fun addPartThree(value: CommonTopicResponse.Response) = withContext(Dispatchers.IO) {
        val json = Json.encodeToString(value).encryptWithKey(KEY)
        settings.putString(PART_THREE, json)
    }

    suspend fun getPartThree(): CommonTopicResponse.Response? = withContext(Dispatchers.IO) {
        if (::partThreeResponse.isInitialized) return@withContext partThreeResponse


        val json = settings.getString(PART_THREE, "").decryptWithKey(KEY)
        if (json.isEmpty()) return@withContext null

        return@withContext try {
            Json.decodeFromString(json)
        } catch (e: Exception) {
            e.printStackTrace()
            println("getPartThree error ${e.message}")

            null
        }
    }
}

@OptIn(ExperimentalEncodingApi::class)
private suspend fun String.encryptWithKey(key: String): String = withContext(Dispatchers.Default) {
    val inputBytes = encodeToByteArray()
    val keyBytes = key.encodeToByteArray()
    for (i in inputBytes.indices) {
        inputBytes[i] = inputBytes[i] xor keyBytes[i % keyBytes.size]
    }
    Base64.encode(inputBytes)
}

@OptIn(ExperimentalEncodingApi::class)
private suspend fun String.decryptWithKey(key: String): String = withContext(Dispatchers.Default) {
    val decodedBytes = Base64.decode(this@decryptWithKey)
    val keyBytes = key.encodeToByteArray()
    for (i in decodedBytes.indices) {
        decodedBytes[i] = decodedBytes[i] xor keyBytes[i % keyBytes.size]
    }
    decodedBytes.decodeToString()
}

@OptIn(ExperimentalEncodingApi::class)
private fun String.encrypt(key: String): String {
    val inputBytes = encodeToByteArray()
    val keyBytes = key.encodeToByteArray()
    for (i in inputBytes.indices) {
        inputBytes[i] = inputBytes[i] xor keyBytes[i % keyBytes.size]
    }
    return Base64.encode(inputBytes)
}

@OptIn(ExperimentalEncodingApi::class)
private fun String.decrypt(key: String): String {
    val decodedBytes = Base64.decode(this)
    val keyBytes = key.encodeToByteArray()
    for (i in decodedBytes.indices) {
        decodedBytes[i] = decodedBytes[i] xor keyBytes[i % keyBytes.size]
    }
    return decodedBytes.decodeToString()
}

