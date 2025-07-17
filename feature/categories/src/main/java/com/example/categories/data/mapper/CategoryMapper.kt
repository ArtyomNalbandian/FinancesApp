package com.example.categories.data.mapper

import com.example.common.model.category.Category
import com.example.database.entity.CategoryEntity
import com.example.network.dto.category.CategoryDto

internal fun CategoryDto.toCategory() = Category(
    id = id.toString(),
    name = name,
    emoji = emoji,
    isIncome = isIncome
)

internal fun CategoryEntity.toCategory() = Category(
    id = id.toString(),
    name = name,
    emoji = emoji,
    isIncome = isIncome
)
