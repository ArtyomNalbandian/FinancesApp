package com.example.categories.data.repository

import com.example.categories.data.mapper.toCategory
import com.example.categories.domain.repository.CategoriesRepository
import com.example.common.model.category.Category
import com.example.network.api.CategoriesApi
import javax.inject.Inject

internal class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesApi: CategoriesApi
) : CategoriesRepository {

    override suspend fun getCategories(): List<Category> {
        return categoriesApi.getCategories().map { categoryDto ->
            categoryDto.toCategory()
        }
    }
}
