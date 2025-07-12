package com.example.categories.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to Categories screen
@Serializable
data object CategoriesScreenRoute

fun NavGraphBuilder.categoriesScreen() {
    composable<CategoriesScreenRoute> {
        CategoriesScreen()
    }
}
