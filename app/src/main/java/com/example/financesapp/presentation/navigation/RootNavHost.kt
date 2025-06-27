package com.example.financesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financesapp.presentation.screens.account.AccountScreen
import com.example.financesapp.presentation.screens.articles.ArticlesTestScreen
import com.example.financesapp.presentation.screens.expenses.ExpensesScreen
import com.example.financesapp.presentation.screens.history.history_expenses.ExpensesHistoryScreen
import com.example.financesapp.presentation.screens.history.history_income.IncomeHistoryScreen
import com.example.financesapp.presentation.screens.income.IncomeScreen
import com.example.financesapp.presentation.screens.settings.SettingsTestScreen


@Composable
fun RootNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.ExpensesGraph.route,
        modifier = modifier
    ) {
        navigation(
            startDestination = ScreenRoute.Expenses.route,
            route = ScreenRoute.ExpensesGraph.route
        ) {
            composable(ScreenRoute.Expenses.route) {
                ExpensesScreen()
            }
            composable(ScreenRoute.History("expenses").route) {
                ExpensesHistoryScreen()
            }
        }

        navigation(
            startDestination = ScreenRoute.Income.route,
            route = ScreenRoute.IncomeGraph.route
        ) {
            composable(ScreenRoute.Income.route) {
                IncomeScreen()
            }
            composable(ScreenRoute.History("income").route) {
                IncomeHistoryScreen()
            }
        }

        navigation(
            startDestination = ScreenRoute.Account.route,
            route = ScreenRoute.AccountGraph.route
        ) {
            composable(ScreenRoute.Account.route) {
                AccountScreen()
            }
        }

        navigation(
            startDestination = ScreenRoute.Articles.route,
            route = ScreenRoute.ArticlesGraph.route
        ) {
            composable(ScreenRoute.Articles.route) {
                ArticlesTestScreen()

            }
        }

        navigation(
            startDestination = ScreenRoute.Settings.route,
            route = ScreenRoute.SettingsGraph.route
        ) {
            composable(ScreenRoute.Settings.route) {
                SettingsTestScreen()
            }
        }
    }
}
