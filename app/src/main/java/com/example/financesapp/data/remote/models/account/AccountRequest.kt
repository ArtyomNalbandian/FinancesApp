package com.example.financesapp.data.remote.models.account

// To create/update account
data class AccountRequest(
    val name: String,
    val balance: String,
    val currency: String
)
