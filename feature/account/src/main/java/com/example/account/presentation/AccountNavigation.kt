package com.example.account.presentation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// route to Account screen
@Serializable
data object AccountScreenRoute

fun NavGraphBuilder.accountScreen(navigateToEditAccount: () -> Unit) {
    composable<AccountScreenRoute> {
        Log.d("testLog", "accountScreen")
        AccountScreen(
            navigateToEditAccount = { navigateToEditAccount() }
        )
    }
}