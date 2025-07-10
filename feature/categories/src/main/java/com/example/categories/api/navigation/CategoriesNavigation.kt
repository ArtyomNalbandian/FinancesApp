package com.example.categories.api.navigation

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.categories.api.di.DaggerCategoriesComponent
import com.example.categories.impl.presentation.CategoriesScreen
import com.example.network.di.DaggerNetworkComponent
import kotlinx.serialization.Serializable

// route to Categories screen
@Serializable
data object CategoriesScreenRoute

fun NavGraphBuilder.categoriesScreen(categoriesViewModelFactory: ViewModelProvider.Factory) {
    composable<CategoriesScreenRoute> {
        val networkComponent = DaggerNetworkComponent.create()
        val categoriesComponent = DaggerCategoriesComponent.factory().create(networkApi = networkComponent)
        CategoriesScreen(categoriesViewModelFactory = categoriesComponent.viewModelFactory())
    }
}
