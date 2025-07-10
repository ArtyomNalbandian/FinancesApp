package com.example.categories.impl.data.repository

import com.example.categories.impl.data.mapper.toCategory
import com.example.categories.impl.domain.repository.CategoriesRepository
import com.example.common.model.category.Category
import com.example.network.api.CategoriesApi
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesApi: CategoriesApi
) : CategoriesRepository {

    override suspend fun getCategories(): List<Category> {
        return categoriesApi.getCategories().map { categoryDto ->
            categoryDto.toCategory()
        }
    }
}
