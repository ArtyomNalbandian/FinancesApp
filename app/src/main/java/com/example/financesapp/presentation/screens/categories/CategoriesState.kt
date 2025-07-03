package com.example.financesapp.presentation.screens.categories

import com.example.financesapp.domain.models.categories.Category

sealed interface CategoriesState {

    data object Loading : CategoriesState
    data class Content(
        val categories: List<Category>,
    ) : CategoriesState

    data class Error(val message: String) : CategoriesState
}