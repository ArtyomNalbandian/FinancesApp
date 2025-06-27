package com.example.financesapp.domain.models.income

data class Income(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val leadingIcon: String?,
    val amount: String,
    val currency: String,
    val transactionDate: String,
)
