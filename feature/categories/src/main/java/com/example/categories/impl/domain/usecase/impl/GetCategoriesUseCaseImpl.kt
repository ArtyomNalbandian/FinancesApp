package com.example.categories.impl.domain.usecase.impl

import com.example.categories.impl.domain.usecase.interfaces.GetCategoriesUseCase
import com.example.categories.impl.domain.repository.CategoriesRepository
import com.example.common.model.category.Category
import javax.inject.Inject

class GetCategoriesUseCaseImpl @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : GetCategoriesUseCase {

    override suspend fun invoke(): List<Category> {
        return categoriesRepository.getCategories()
    }
}
