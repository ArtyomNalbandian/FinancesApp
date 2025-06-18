package com.example.financesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financesapp.presentation.accounts.AccountsScreen
import com.example.financesapp.presentation.add_account.AddAccountScreen
import com.example.financesapp.presentation.analysis.AnalysisScreen
import com.example.financesapp.presentation.articles.ArticlesTestScreen
import com.example.financesapp.presentation.expenses.ExpensesTestScreen
import com.example.financesapp.presentation.history.HistoryScreen
import com.example.financesapp.presentation.income.IncomeTestScreen
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
//                ExpensesScreen()
                ExpensesTestScreen { navController.navigate(Screen.History("expenses").route) }
            }
            composable(Screen.History("expenses").route) {
                HistoryScreen("expenses")
            }
            composable(Screen.Analysis("expenses").route) {
                AnalysisScreen("expenses")
            }
        }

        navigation(
            startDestination = Screen.Income.route,
            route = Screen.IncomeGraph.route
        ) {
            composable(Screen.Income.route) {
//                IncomeScreen()
                IncomeTestScreen { navController.navigate(Screen.History("income").route) }
            }
            composable(Screen.History("income").route) {
                HistoryScreen("income")
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
//                AccountScreen()
//                AccountTestScreen(
//                    onTrailingIconClick = { navController.navigate(Screen.AddAccount.route) },
//                    onAddAccount = { navController.navigate(Screen.AddAccount.route) }
//                )
//                AccountScreenSuperTest(
//                    onEditClick = {},
//                    onAddAccount = {},
//                )
                AccountsScreen(
                    navigateToEditAccountScreen = {},
                    navigateToCreateAccountScreen = {}
                )
//                AccountScreenTest()
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
//                ArticlesScreen()
                ArticlesTestScreen()

            }
        }

        navigation(
            startDestination = Screen.Settings.route,
            route = Screen.SettingsGraph.route
        ) {
            composable(Screen.Settings.route) {
//                SettingsScreen()
                SettingsTestScreen()
            }
        }
    }
}
