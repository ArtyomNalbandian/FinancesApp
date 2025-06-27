package com.example.financesapp.data.remote.models.account

data class AccountRequestDto(
    val name: String,
    val balance: String,
    val currency: String
)
