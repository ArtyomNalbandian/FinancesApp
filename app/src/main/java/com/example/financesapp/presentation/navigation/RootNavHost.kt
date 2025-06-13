package com.example.financesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financesapp.presentation.navigation.navhosts.AccountNavHost
import com.example.financesapp.presentation.navigation.navhosts.ArticlesNavHost
import com.example.financesapp.presentation.navigation.navhosts.ExpensesNavHost
import com.example.financesapp.presentation.navigation.navhosts.IncomeNavHost
import com.example.financesapp.presentation.navigation.navhosts.SettingsNavHost

@Composable
fun RootNavHost(
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = rootNavController,
        startDestination = "Расходы",
        modifier = modifier
    ) {
        composable("Расходы") {
            ExpensesNavHost()
        }
        composable("Доходы") {
            IncomeNavHost()
        }
        composable("Счет") {
            AccountNavHost()
        }
        composable("Статьи") {
            ArticlesNavHost()
        }
        composable("Настройки") {
            SettingsNavHost()
        }
    }
}

