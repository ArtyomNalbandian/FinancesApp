package com.example.financesapp.domain.expenses

data class Expense(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val leadingIcon: String,
    val amount: String,
    val currency: String,
)