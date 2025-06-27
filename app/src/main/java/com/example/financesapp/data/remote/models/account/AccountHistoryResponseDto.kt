package com.example.financesapp.data.remote.models.account

data class AccountHistoryResponseDto(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryDto>
)
