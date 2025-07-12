package com.example.incomes.presentation.incomes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to Incomes screen
@Serializable
data object IncomesScreenRoute

fun NavGraphBuilder.incomesScreen(
    navigateToHistory: () -> Unit,
    navigateToAddIncome: () -> Unit,
    navigateToEditIncome: (Int) -> Unit
) {
    composable<IncomesScreenRoute> {
        IncomesScreen(
            navigateToHistory = navigateToHistory,
            navigateToAddIncome = navigateToAddIncome,
            navigateToEditIncome = navigateToEditIncome
        )
    }
}
