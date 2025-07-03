package com.example.financesapp.domain.repositories

import com.example.financesapp.domain.models.categories.Category

interface CategoriesRepository {

    suspend fun getCategories(): List<Category>
}