package com.example.financesapp.data.remote.models.transaction

data class AccountBrief(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
