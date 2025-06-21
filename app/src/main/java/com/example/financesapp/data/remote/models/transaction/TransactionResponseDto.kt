package com.example.financesapp.data.remote.models.transaction

import com.example.financesapp.data.remote.models.category.Category

// To get transaction by id
data class TransactionResponseDto(
    val id: Int,
    val account: AccountBrief,
    val category: Category,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)
