package com.example.network.dto.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean,
)
