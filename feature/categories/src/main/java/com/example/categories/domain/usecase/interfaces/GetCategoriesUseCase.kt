package com.example.categories.domain.usecase.interfaces

import com.example.common.model.category.Category

internal interface GetCategoriesUseCase {

    suspend operator fun invoke(): List<Category>
}
