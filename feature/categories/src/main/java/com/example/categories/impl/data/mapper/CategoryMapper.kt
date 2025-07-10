package com.example.categories.impl.data.mapper

import com.example.common.model.category.Category
import com.example.network.dto.category.CategoryDto

fun CategoryDto.toCategory() = Category(
    id = id.toString(),
    name = name,
    emoji = emoji,
    isIncome = isIncome
)
