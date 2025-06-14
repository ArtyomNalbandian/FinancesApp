package com.example.financesapp.presentation.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.presentation.expenses.ExpensesScreen
import com.example.financesapp.presentation.history.HistoryScreen

@Composable
fun ExpensesNavHost(navController: NavHostController) {

    NavHost(navController, startDestination = "expenses_main") {
        composable("expenses_main") {
            ExpensesScreen()
        }
        composable("expenses_history") {
            HistoryScreen()
        }

    }
}