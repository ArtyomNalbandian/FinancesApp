package com.example.categories.domain.repository

import com.example.common.model.category.Category

interface CategoriesRepository {

    suspend fun getCategories(): List<Category>
}
