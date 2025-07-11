package com.example.categories.domain.usecase.impl

import com.example.categories.domain.usecase.interfaces.GetCategoriesUseCase
import com.example.categories.domain.repository.CategoriesRepository
import com.example.common.model.category.Category
import javax.inject.Inject

internal class GetCategoriesUseCaseImpl @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : GetCategoriesUseCase {

    override suspend fun invoke(): List<Category> {
        return categoriesRepository.getCategories()
    }
}
