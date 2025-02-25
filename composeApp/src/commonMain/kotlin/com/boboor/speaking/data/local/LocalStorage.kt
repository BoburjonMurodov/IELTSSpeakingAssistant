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
    val PART_ONE_PREFIX = "SPEAKING_ONE_PART_".encrypt(getKey())
    val PART_THREE_PREFIX = "PART_THREE_PART_".encrypt(getKey())
    private val CHUNK_SIZE = 4000 // Adjust based on storage limits

    private fun getRandomString(length: Int = Random.nextInt(12, 55)): String {
        val charset = "AAAB"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    private fun getKey(): String {
        val key = settings.getString("KEY", "")
        if (key.isEmpty()) {
            settings.putString("KEY", getRandomString())
        }
        return settings.getString("KEY", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
    }

    private lateinit var partOneResponse: CommonTopicResponse.Response
    private lateinit var partThreeResponse: CommonTopicResponse.Response

    suspend fun addPartOne(value: CommonTopicResponse.Response) = storeInParts(PART_ONE_PREFIX, value)
    suspend fun getPartOne(): CommonTopicResponse.Response? = retrieveFromParts(PART_ONE_PREFIX)

    suspend fun addPartThree(value: CommonTopicResponse.Response) = storeInParts(PART_THREE_PREFIX, value)
    suspend fun getPartThree(): CommonTopicResponse.Response? = retrieveFromParts(PART_THREE_PREFIX)

    private suspend fun storeInParts(prefix: String, value: CommonTopicResponse.Response) {
        val json = Json.encodeToString(value).encryptWithKey(getKey())
        val parts = json.chunked(CHUNK_SIZE)

        settings.putInt("${prefix}_COUNT", parts.size)
        parts.forEachIndexed { index, part ->
            settings.putString("$prefix$index", part)
        }
    }

    private suspend fun retrieveFromParts(prefix: String): CommonTopicResponse.Response? {
        val count = settings.getInt("${prefix}_COUNT", 0)
        if (count == 0) return null

        val json = (0 until count)
            .mapNotNull { settings.getString("$prefix$it", "") }
            .joinToString("")
            .decryptWithKey(getKey())

        return try {
            Json.decodeFromString(json)
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error retrieving data: ${e.message}")
            null
        }
    }
}


@OptIn(ExperimentalEncodingApi::class)
private suspend fun String.encryptWithKey(key: String): String = withContext(Dispatchers.Default) {

//    val inputBytes = encodeToByteArray()
//    val keyBytes = key.encodeToByteArray()
//    for (i in inputBytes.indices) {
//        inputBytes[i] = inputBytes[i] xor keyBytes[i % keyBytes.size]
//    }
//    Base64.encode(inputBytes)
    return@withContext this@encryptWithKey
}

@OptIn(ExperimentalEncodingApi::class)
private suspend fun String.decryptWithKey(key: String): String = withContext(Dispatchers.Default) {
//    val decodedBytes = Base64.decode(this@decryptWithKey)
//    val keyBytes = key.encodeToByteArray()
//    for (i in decodedBytes.indices) {
//        decodedBytes[i] = decodedBytes[i] xor keyBytes[i % keyBytes.size]
//    }
//    decodedBytes.decodeToString()

    return@withContext this@decryptWithKey

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

