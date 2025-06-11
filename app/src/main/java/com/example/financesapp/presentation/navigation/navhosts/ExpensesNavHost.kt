package com.example.financesapp.presentation.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.presentation.expenses.ExpensesScreen

@Composable
fun ExpensesNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "expenses_main") {
        composable("expenses_main") {
            ExpensesScreen()
        }

    }
}