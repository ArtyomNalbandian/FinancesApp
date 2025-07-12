package com.example.expenses.presentation.expenses_edit

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.expensesEditScreen(
    onNavigateBack: () -> Unit
) {
    composable(
        route = "expenses_edit/{expenseId}",
        arguments = listOf(
            navArgument("expenseId") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val expenseId = backStackEntry.arguments?.getString("expenseId")?.toIntOrNull()
            ?: error("No expenseId provided")

        Log.d("testLog", "Received expenseId: $expenseId")

        ExpensesEditScreen(
            expenseId = expenseId,
            navigateBack = onNavigateBack
        )
    }
}
