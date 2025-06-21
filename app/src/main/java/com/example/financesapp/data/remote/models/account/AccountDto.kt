package com.example.financesapp.data.remote.models.account

data class AccountDto(
    val id: Int,
    val userId: Long,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)
