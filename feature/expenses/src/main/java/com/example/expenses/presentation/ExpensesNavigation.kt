package com.example.expenses.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to Expenses screen
@Serializable
data object ExpensesScreenRoute

fun NavGraphBuilder.expensesScreen(navigateToHistory: () -> Unit) {
    composable<ExpensesScreenRoute> {
        ExpensesScreen(
            navigateToHistory = navigateToHistory
        )
    }
}
