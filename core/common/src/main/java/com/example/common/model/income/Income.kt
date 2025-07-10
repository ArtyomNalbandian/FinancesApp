package com.example.common.model.income

data class Income(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val leadingIcon: String?,
    val amount: String,
    val currency: String,
    val transactionDate: String,
)
