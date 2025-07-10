package com.example.common.model.expense

data class Expense(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val leadingIcon: String,
    val amount: String,
    val currency: String,
    val transactionDate: String,
)
