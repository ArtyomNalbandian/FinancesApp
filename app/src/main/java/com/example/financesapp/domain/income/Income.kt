package com.example.financesapp.domain.income

data class Income(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val leadingIcon: String?,
    val amount: String,
    val currency: String,
    val transactionDate: String,
)
