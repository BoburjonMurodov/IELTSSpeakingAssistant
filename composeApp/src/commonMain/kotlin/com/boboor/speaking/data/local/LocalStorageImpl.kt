package com.boboor.speaking.data.local

import com.boboor.speaking.data.remote.models.CommonTopicResponse
import com.boboor.speaking.data.remote.models.PartTwoResponse
import com.boboor.speaking.utils.enums.UpdateFrequency
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import kotlinx.coroutines.Dispatchers
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

interface LocalStorage {
    suspend fun setPartOne(value: CommonTopicResponse.Response)
    suspend fun setPartTwo(value: PartTwoResponse.Response)
    suspend fun setPartThree(value: CommonTopicResponse.Response)

    suspend fun getPartOne(): CommonTopicResponse.Response?
    suspend fun getPartTwo(): PartTwoResponse.Response?
    suspend fun getPartThree(): CommonTopicResponse.Response?

    fun setQuestionsVisibility(value: Boolean)
    fun getQuestionsVisibility(): Boolean

    fun setUpdateFrequency(value: UpdateFrequency)
    fun getUpdateFrequency(): UpdateFrequency

    suspend fun clear()
}


class LocalStorageImpl : LocalStorage {
    private val settings = Settings()
    private val PART_ONE_PREFIX = "SPEAKING_ONE_PART_".encrypt(getKey())
    private val PART_TWO_PREFIX = "SPEAKING_ONE_PART_".encrypt(getKey())
    private val PART_THREE_PREFIX = "PART_THREE_PART_".encrypt(getKey())
    private val QUESTION_VISIBILITY = "QUESTION_VISIBILITY".encrypt(getKey())
    private val UPDATE_FREQUENCY = "UPDATE_FREQUENCY".encrypt(getKey())
    private val CHUNK_SIZE = 4000

    override suspend fun setPartOne(value: CommonTopicResponse.Response) = storeInParts(PART_ONE_PREFIX, value)
    override suspend fun setPartTwo(value: PartTwoResponse.Response) = storeInParts(PART_TWO_PREFIX, value)
    override suspend fun setPartThree(value: CommonTopicResponse.Response) = storeInParts(PART_THREE_PREFIX, value)

    override suspend fun getPartOne(): CommonTopicResponse.Response? = retrieveFromParts(PART_ONE_PREFIX)
    override suspend fun getPartTwo(): PartTwoResponse.Response? = retrieveFromParts(PART_TWO_PREFIX)
    override suspend fun getPartThree(): CommonTopicResponse.Response? = retrieveFromParts(PART_THREE_PREFIX)

    private fun getRandomString(length: Int = Random.nextInt(12, 55)): String {
        val charset = "A%#(LKB_-+G"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    private fun getKey(): String {
        val key = settings.getString("KEY", "")
        if (key.isEmpty()) {
            settings.putString("KEY", getRandomString())
        }
        return settings.getString("KEY", "A%#(LKB_-+G")
    }

    override fun setQuestionsVisibility(value: Boolean) {
        settings.putBoolean(QUESTION_VISIBILITY, value)
    }

    override fun getQuestionsVisibility(): Boolean = settings.getBoolean(QUESTION_VISIBILITY, false)
    override fun setUpdateFrequency(value: UpdateFrequency) {
        settings.putString(UPDATE_FREQUENCY, value.name.encrypt(getKey()))
    }

    override fun getUpdateFrequency(): UpdateFrequency {
        val value = settings.get<String>(UPDATE_FREQUENCY)?.decrypt(getKey()) ?: UpdateFrequency.EVERY_APP_OPENING.name
        return UpdateFrequency.valueOf(value)
    }

    override suspend fun clear() { settings.clear() }


    private suspend inline fun <reified T> storeInParts(prefix: String, value: T) {
        val json = Json.encodeToString(value).encryptWithKey(getKey())
        val parts = json.chunked(CHUNK_SIZE)

        settings.putInt("${prefix}_COUNT", parts.size)
        parts.forEachIndexed { index, part ->
            settings.putString("$prefix$index", part)
        }
    }
    private suspend inline fun <reified T> retrieveFromParts(prefix: String): T?{
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

    return@withContext Base64.encode(inputBytes)
}

@OptIn(ExperimentalEncodingApi::class)
private suspend fun String.decryptWithKey(key: String): String = withContext(Dispatchers.Default) {
    val decodedBytes = Base64.decode(this@decryptWithKey)
    val keyBytes = key.encodeToByteArray()
    for (i in decodedBytes.indices) {
        decodedBytes[i] = decodedBytes[i] xor keyBytes[i % keyBytes.size]
    }
    return@withContext decodedBytes.decodeToString()
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