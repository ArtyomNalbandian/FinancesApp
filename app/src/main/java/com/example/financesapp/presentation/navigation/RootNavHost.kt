package com.example.financesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financesapp.presentation.account.AccountScreen
import com.example.financesapp.presentation.add_account.AddAccountScreen
import com.example.financesapp.presentation.analysis.AnalysisScreen
import com.example.financesapp.presentation.articles.ArticlesTestScreen
import com.example.financesapp.presentation.expenses.ExpensesScreen
import com.example.financesapp.presentation.history.ExpensesHistoryScreen
import com.example.financesapp.presentation.history.IncomeHistoryScreen
import com.example.financesapp.presentation.income.IncomeScreen
import com.example.financesapp.presentation.settings.SettingsTestScreen


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
            composable(Screen.History("expenses").route) {
                ExpensesHistoryScreen(navController = navController)
            }
            composable(Screen.Analysis("expenses").route) {
                AnalysisScreen("expenses")
            }
            composable(Screen.AddExpense.route) {
                // TODO()
            }
        }

        navigation(
            startDestination = Screen.Income.route,
            route = Screen.IncomeGraph.route
        ) {
            composable(Screen.Income.route) {
                IncomeScreen()
            }
            composable(Screen.History("income").route) {
                IncomeHistoryScreen()
            }
            composable(Screen.Analysis("income").route) {
                AnalysisScreen("income")
            }
        }

        navigation(
            startDestination = Screen.Account.route,
            route = Screen.AccountGraph.route
        ) {
            composable(Screen.Account.route) {
                AccountScreen()
            }
            composable(Screen.AddAccount.route) {
                AddAccountScreen(
                    onLeadingIconClick = { navController.popBackStack() },
                    onTrailingIconClick = { navController.popBackStack() }
                )
            }
        }

        navigation(
            startDestination = Screen.Articles.route,
            route = Screen.ArticlesGraph.route
        ) {
            composable(Screen.Articles.route) {
                ArticlesTestScreen()

            }
        }

        navigation(
            startDestination = Screen.Settings.route,
            route = Screen.SettingsGraph.route
        ) {
            composable(Screen.Settings.route) {
                SettingsTestScreen()
            }
        }
    }
}
