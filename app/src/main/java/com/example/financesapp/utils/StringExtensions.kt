package com.example.financesapp.utils

fun String.toCurrencySymbol(): String {
    return when(this.uppercase()) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> this
    }
}

fun String.formatAmount(): String {
    return try {
        val amount = this.replace(".0", "").replace(".", ",")
        amount.reversed()
            .chunked(3)
            .joinToString(" ")
            .reversed()
    } catch (e: Exception) {
        this
    }
}