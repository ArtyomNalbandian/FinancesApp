package com.example.categories.impl.domain.repository

import com.example.common.model.category.Category

interface CategoriesRepository {

    suspend fun getCategories(): List<Category>
}
