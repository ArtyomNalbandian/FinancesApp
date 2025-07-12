package com.example.expenses.presentation.expenses_add

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to ExpensesAdd screen
@Serializable
data object ExpensesAddScreenRoute

fun NavGraphBuilder.expensesAddScreen(
    navigateBack: () -> Unit,
) {
    composable<ExpensesAddScreenRoute> {
        ExpensesAddScreen(
            navigateBack = navigateBack,
        )
    }
}
