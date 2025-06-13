package com.example.financesapp.domain.income

data class Income(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val amount: String,
    val currency: String,
)
