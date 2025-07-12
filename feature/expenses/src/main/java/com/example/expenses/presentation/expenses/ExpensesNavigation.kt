package com.example.expenses.presentation.expenses

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to Expenses screen
@Serializable
data object ExpensesScreenRoute

fun NavGraphBuilder.expensesScreen(
    navigateToHistory: () -> Unit,
    navigateToAddExpense: () -> Unit,
    navigateToEditExpense: (Int) -> Unit
) {
    composable<ExpensesScreenRoute> {
        ExpensesScreen(
            navigateToHistory = navigateToHistory,
            navigateToAddExpense = navigateToAddExpense,
            navigateToEditExpense = navigateToEditExpense
        )
    }
}
