package com.example.financesapp.domain.usecases.interfaces

import com.example.financesapp.domain.models.categories.Category

interface GetCategoriesUseCase {

    suspend operator fun invoke(): List<Category>
}
