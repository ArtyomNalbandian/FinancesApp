package com.example.common.model.account

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: Int,
    val name: String = "",
    val balance: String,
    val currency: String,
)
