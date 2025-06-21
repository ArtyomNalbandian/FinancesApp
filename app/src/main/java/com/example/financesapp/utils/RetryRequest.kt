package com.example.financesapp.utils

import android.util.Log
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> retryRequest(
    maxRetries: Int = 3,
    delayMillis: Long = 2000,
    shouldRetry: (Throwable) -> Boolean = { true },
    block: suspend () -> T
): T {
    var lastError: Throwable? = null

    repeat(maxRetries) { attempt ->
        try {
            return block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            val willRetry = shouldRetry(e)
            if (!willRetry) {
                Log.d("testLog", "NOT retrying — ${e::class.java.simpleName}")
                throw e
            }

            lastError = e
            Log.d("testLog", "retry attempt ${attempt + 1} --- $e")

            if (attempt < maxRetries - 1) {
                delay(delayMillis)
            }
        }
    }

    throw lastError ?: Exception("Unknown error after $maxRetries attempts")
}


