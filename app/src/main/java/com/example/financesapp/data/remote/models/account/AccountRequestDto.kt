package com.example.financesapp.data.remote.models.account

// To create/update account
data class AccountRequestDto(
    val name: String,
    val balance: String,
    val currency: String
)
