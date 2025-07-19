package com.example.incomes.presentation.incomes_analysis

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to IncomesAnalysis screen
@Serializable
data object IncomesAnalysisScreenRoute

fun NavGraphBuilder.incomesAnalysisScreen(
    navigateBack: () -> Unit
) {
    composable<IncomesAnalysisScreenRoute> {
        IncomesAnalysisScreen(
            navigateBack = navigateBack
        )
    }
}
