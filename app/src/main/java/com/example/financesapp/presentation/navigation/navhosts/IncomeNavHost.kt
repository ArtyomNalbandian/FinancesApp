package com.example.financesapp.presentation.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.presentation.income.IncomeScreen

@Composable
fun IncomeNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "income_main") {
        composable("income_main") {
            IncomeScreen()
        }
    }
}