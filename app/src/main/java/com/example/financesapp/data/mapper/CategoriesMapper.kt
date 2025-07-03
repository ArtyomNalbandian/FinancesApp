package com.example.financesapp.data.mapper

import com.example.financesapp.data.remote.models.category.CategoryDto
import com.example.financesapp.domain.models.categories.Category

fun CategoryDto.toCategory() = Category(
    id = id.toString(),
    name = name,
    emoji = emoji,
    isIncome = isIncome
)
