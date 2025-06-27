package com.example.financesapp.domain.models.account

data class Account(
    val id: Int,
    val name: String = "",
    val balance: String,
    val currency: String,
)
