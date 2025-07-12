package com.example.incomes.presentation.incomes_history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to IncomesHistory screen
@Serializable
data object IncomesHistoryScreenRoute

fun NavGraphBuilder.incomesHistoryScreen(
    navigateBack: () -> Unit,
    navigateToAnalysis: () -> Unit
) {
    composable<IncomesHistoryScreenRoute> {
        IncomesHistoryScreen(
            navigateBack = navigateBack,
            navigateToAnalysis = navigateToAnalysis
        )
    }
}
