package com.example.incomes.presentation.incomes_add

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to IncomesAdd screen
@Serializable
data object IncomesAddScreenRoute

fun NavGraphBuilder.incomesAddScreen(
    navigateBack: () -> Unit,
) {
    composable<IncomesAddScreenRoute> {
        IncomesAddScreen(
            navigateBack = navigateBack,
        )
    }
}
