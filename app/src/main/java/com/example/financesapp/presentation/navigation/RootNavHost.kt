package com.example.financesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financesapp.presentation.account.AccountScreen
import com.example.financesapp.presentation.articles.ArticlesScreen
import com.example.financesapp.presentation.expenses.ExpensesScreen
import com.example.financesapp.presentation.income.IncomeScreen
import com.example.financesapp.presentation.navigation.navhosts.AccountNavHost
import com.example.financesapp.presentation.navigation.navhosts.ArticlesNavHost
import com.example.financesapp.presentation.navigation.navhosts.ExpensesNavHost
import com.example.financesapp.presentation.navigation.navhosts.IncomeNavHost
import com.example.financesapp.presentation.navigation.navhosts.SettingsNavHost
import com.example.financesapp.presentation.navigation.routes.Screen
import com.example.financesapp.presentation.settings.SettingsScreen

@Composable
fun RootNavHost(
    expensesNavController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = rootNavController,
        startDestination = "Расходы",
        modifier = modifier
    ) {
        composable("Расходы") {
            ExpensesNavHost(expensesNavController)
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

@Composable
fun RootNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ExpensesGraph.route,
        modifier = modifier
    ) {
        navigation(
            startDestination = Screen.Expenses.route,
            route = Screen.ExpensesGraph.route
        ) {
            composable(Screen.Expenses.route) {
                ExpensesScreen()
            }
        }

        navigation(
            startDestination = Screen.Income.route,
            route = Screen.IncomeGraph.route
        ) {
            composable(Screen.Income.route) {
                IncomeScreen()
            }
        }

        navigation(
            startDestination = Screen.Account.route,
            route = Screen.AccountGraph.route
        ) {
            composable(Screen.Account.route) {
                AccountScreen()
            }
        }

        navigation(
            startDestination = Screen.Articles.route,
            route = Screen.ArticlesGraph.route
        ) {
            composable(Screen.Articles.route) {
                ArticlesScreen()
            }
        }

        navigation(
            startDestination = Screen.Settings.route,
            route = Screen.SettingsGraph.route
        ) {
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}
