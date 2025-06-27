package com.example.financesapp.data.remote.models.account

data class AccountHistoryDto(
    val id: Int,
    val accountId: Int,
    val changeType: String,
    val previousState: AccountStateDto?,
    val newState: AccountStateDto,
    val changeTimestamp: String,
    val createdAt: String
)
