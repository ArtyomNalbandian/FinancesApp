package com.example.network.dto.transaction

// To create transaction
data class TransactionRequestDto(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?
)
