package com.example.financesapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenRoute {

    // Top level routes
    @Serializable
    data object ExpensesGraph : ScreenRoute

    @Serializable
    data object IncomeGraph : ScreenRoute

    @Serializable
    data object AccountGraph : ScreenRoute

    @Serializable
    data object ArticlesGraph : ScreenRoute

    @Serializable
    data object SettingsGraph : ScreenRoute
}
