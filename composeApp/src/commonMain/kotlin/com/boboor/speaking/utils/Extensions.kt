package com.boboor.speaking.utils

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import com.boboor.speaking.TimeUtil
import kotlinx.coroutines.CancellationException


/*
    Created by Boburjon Murodov 20/02/25 at 10:47
*/


inline fun <T, R> T.resultOf(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}


