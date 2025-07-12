package com.example.network.dto.account

data class AccountDto(
    val id: Int,
    val userId: Long,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)
