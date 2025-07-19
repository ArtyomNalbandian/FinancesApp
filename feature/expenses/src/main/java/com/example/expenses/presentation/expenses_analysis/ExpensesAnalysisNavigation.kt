package com.example.expenses.presentation.expenses_analysis

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to ExpensesAnalysis screen
@Serializable
data object ExpensesAnalysisScreenRoute

fun NavGraphBuilder.expensesAnalysisScreen(
    navigateBack: () -> Unit
) {
    composable<ExpensesAnalysisScreenRoute> {
        ExpensesAnalysisScreen(
            navigateBack = navigateBack
        )
    }
}
