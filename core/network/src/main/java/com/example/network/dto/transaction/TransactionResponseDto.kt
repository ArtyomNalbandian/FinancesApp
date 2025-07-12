package com.example.network.dto.transaction

import com.example.network.dto.category.CategoryDto
import com.google.gson.annotations.SerializedName

// To get transaction by id
data class TransactionResponseDto(
    val id: Int,
    val account: AccountBrief,
    @SerializedName("category")
    val categoryDto: CategoryDto,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)
