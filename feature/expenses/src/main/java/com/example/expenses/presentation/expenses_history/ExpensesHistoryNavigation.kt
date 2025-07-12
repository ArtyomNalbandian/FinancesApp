package com.example.expenses.presentation.expenses_history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to ExpensesHistory screen
@Serializable
data object ExpensesHistoryScreenRoute

fun NavGraphBuilder.expensesHistoryScreen(
    navigateBack: () -> Unit,
    navigateToAnalysis: () -> Unit
) {
    composable<ExpensesHistoryScreenRoute> {
        ExpensesHistoryScreen(
            navigateBack = navigateBack,
            navigateToAnalysis = navigateToAnalysis
        )
    }
}
