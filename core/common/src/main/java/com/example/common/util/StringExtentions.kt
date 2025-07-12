package com.example.common.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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
        // Парсим как Instant (ISO 8601) и конвертируем в LocalDateTime
        val instant = Instant.parse(this)
        instant.atZone(ZoneOffset.UTC).toLocalDateTime()
    } catch (e: Exception) {
        null
    }
}

//fun String.toLocalDateTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): LocalDateTime {
//    val formatter = DateTimeFormatter.ofPattern(pattern)
//    return LocalDateTime.parse(this, formatter)
//}
