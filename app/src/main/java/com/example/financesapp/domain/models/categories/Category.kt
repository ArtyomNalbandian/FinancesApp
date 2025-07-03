package com.example.financesapp.domain.models.categories

data class Category(
    val id: String,
    val name: String,
    val emoji: String,
    val isIncome: Boolean,
)
