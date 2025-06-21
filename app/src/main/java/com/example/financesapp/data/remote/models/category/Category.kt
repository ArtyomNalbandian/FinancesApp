package com.example.financesapp.data.remote.models.category

// To get categories
data class Category(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)
