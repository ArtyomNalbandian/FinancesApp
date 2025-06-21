package com.example.financesapp.data.remote.models.transaction

// To create transaction
data class TransactionRequest(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?
)
