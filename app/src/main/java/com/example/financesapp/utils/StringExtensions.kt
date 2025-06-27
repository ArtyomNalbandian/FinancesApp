package com.example.financesapp.utils

fun String.toCurrencySymbol(): String {
    return when (this.uppercase()) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> this
    }
}
