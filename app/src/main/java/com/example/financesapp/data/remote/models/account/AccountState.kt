package com.example.financesapp.data.remote.models.account

data class AccountState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)