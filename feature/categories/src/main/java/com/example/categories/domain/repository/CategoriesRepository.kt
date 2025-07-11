package com.example.categories.domain.repository

import com.example.common.model.category.Category

internal interface CategoriesRepository {

    suspend fun getCategories(): List<Category>
}
