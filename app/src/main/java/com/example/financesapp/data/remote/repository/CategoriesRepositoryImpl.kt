package com.example.financesapp.data.remote.repository

import com.example.financesapp.data.mapper.toCategory
import com.example.financesapp.data.remote.api.CategoriesApi
import com.example.financesapp.domain.models.categories.Category
import com.example.financesapp.domain.repositories.CategoriesRepository
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
