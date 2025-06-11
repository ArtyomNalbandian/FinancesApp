package com.example.financesapp.presentation.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.presentation.account.AccountScreen

@Composable
fun AccountNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "account_main") {
        composable("account_main") {
            AccountScreen()
        }
    }
}