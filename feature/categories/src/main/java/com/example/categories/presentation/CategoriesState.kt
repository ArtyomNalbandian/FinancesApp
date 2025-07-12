package com.example.categories.presentation

import com.example.common.model.category.Category

internal sealed interface CategoriesState {

    data object Loading : CategoriesState
    data class Content(
        val categories: List<Category>,
    ) : CategoriesState

    data class Error(val message: String) : CategoriesState
}
