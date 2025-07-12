package com.example.network.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

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
                throw e
            }
            lastError = e

            if (attempt < maxRetries - 1) {
                delay(delayMillis)
            }
        }
    }

    throw lastError ?: Exception("Unknown error after $maxRetries attempts")
}