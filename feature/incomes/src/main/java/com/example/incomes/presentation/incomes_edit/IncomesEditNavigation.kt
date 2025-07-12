package com.example.incomes.presentation.incomes_edit

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.incomesEditScreen(
    onNavigateBack: () -> Unit
) {
    composable(
        route = "incomes_edit/{incomeId}",
        arguments = listOf(
            navArgument("incomeId") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val incomeId = backStackEntry.arguments?.getString("incomeId")?.toIntOrNull()
            ?: error("No incomeId provided")

        Log.d("testLog", "Received incomeId: $incomeId")

        IncomesEditScreen(
            incomeId = incomeId,
            navigateBack = onNavigateBack
        )
    }
}
