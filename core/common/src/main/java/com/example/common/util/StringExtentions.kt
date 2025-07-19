package com.example.common.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun String.toCurrencySymbol(): String {
    return when (this.uppercase()) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> this
    }
}

fun String.toCurrencyFromSymbol(): String {
    return when (this) {
        "₽" -> "RUB"
        "$" -> "USD"
        "€" -> "EUR"
        else -> this
    }
}

fun String.toLocalDateTime(): LocalDateTime? {
    return try {
        val instant = Instant.parse(this)
        instant.atZone(ZoneOffset.UTC).toLocalDateTime()
    } catch (e: Exception) {
        null
    }
}
