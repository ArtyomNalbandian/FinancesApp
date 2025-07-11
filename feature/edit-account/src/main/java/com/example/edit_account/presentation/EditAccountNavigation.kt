package com.example.edit_account.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to EditAccount screen
@Serializable
data object EditAccountScreenRoute

fun NavGraphBuilder.editAccountScreen(navigateBack: () -> Unit) {
    composable<EditAccountScreenRoute> {
        EditAccountScreen(
            navigateBack = navigateBack
        )
    }
}
