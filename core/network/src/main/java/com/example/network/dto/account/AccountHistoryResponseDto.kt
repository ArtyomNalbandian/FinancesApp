package com.example.network.dto.account

data class AccountHistoryResponseDto(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryDto>
)
