package com.example.categories.data.repository

import com.example.categories.data.mapper.toCategory
import com.example.categories.domain.repository.CategoriesRepository
import com.example.common.model.category.Category
import com.example.database.dao.CategoryDao
import com.example.database.entity.CategoryEntity
import com.example.network.api.CategoriesApi
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesApi: CategoriesApi,
    private val categoryDao: CategoryDao
) : CategoriesRepository {

    override suspend fun getCategories(): List<Category> {
        return try {
            val dtos = categoriesApi.getCategories()
            val entities = dtos.map {
                CategoryEntity(
                    id = it.id,
                    name = it.name,
                    emoji = it.emoji,
                    isIncome = it.isIncome,
                    isDirty = false
                )
            }
            categoryDao.insertAll(entities)
            entities.map { it.toCategory() }
        } catch (e: Exception) {
            // fallback на локальную БД
            categoryDao.getAll().first().map { it.toCategory() }
        }
    }

//    override suspend fun getCategories(): List<Category> {
//        return categoriesApi.getCategories().map { categoryDto ->
//            categoryDto.toCategory()
//        }
//    }
}
