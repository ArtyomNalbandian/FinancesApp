package com.example.financesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.financesapp.presentation.screens.account.AccountScreen
import com.example.financesapp.presentation.screens.account.AccountViewModel
import com.example.financesapp.presentation.screens.categories.CategoriesScreen
import com.example.financesapp.presentation.screens.edit_account.EditAccountScreen
import com.example.financesapp.presentation.screens.expenses.ExpensesScreen
import com.example.financesapp.presentation.screens.history.history_expenses.ExpensesHistoryScreen
import com.example.financesapp.presentation.screens.history.history_income.IncomeHistoryScreen
import com.example.financesapp.presentation.screens.income.IncomeScreen
import com.example.financesapp.presentation.screens.settings.SettingsScreen
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun RootNavGraph(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.ExpensesGraph.route,
        modifier = modifier
    ) {
        addExpensesGraph(viewModelFactory, navController)
        addIncomeGraph(viewModelFactory, navController)
        addAccountGraph(viewModelFactory, navController)
        addArticlesGraph(viewModelFactory)
        addSettingsGraph()
    }
}

private fun NavGraphBuilder.addExpensesGraph(
    viewModelFactory: ViewModelProvider.Factory,
    navController: NavHostController,
) {
    navigation(
        startDestination = ScreenRoute.Expenses.route,
        route = ScreenRoute.ExpensesGraph.route
    ) {
        composable(ScreenRoute.Expenses.route) {
            ExpensesScreen(
                viewModelFactory = viewModelFactory,
                navigateToHistory = { navController.navigate(ScreenRoute.History("expenses").route) }
            )
        }
        composable(ScreenRoute.History("expenses").route) {
            ExpensesHistoryScreen(
                viewModelFactory = viewModelFactory,
                navigateBack = { navController.popBackStack() },
                navigateToAnalysis = { }
            )
        }
    }
}

private fun NavGraphBuilder.addIncomeGraph(
    viewModelFactory: ViewModelProvider.Factory,
    navController: NavHostController,
) {
    navigation(
        startDestination = ScreenRoute.Income.route,
        route = ScreenRoute.IncomeGraph.route
    ) {
        composable(ScreenRoute.Income.route) {
            IncomeScreen(
                viewModelFactory = viewModelFactory,
                navigateToHistory = { navController.navigate(ScreenRoute.History("income").route) }
            )
        }
        composable(ScreenRoute.History("income").route) {
            IncomeHistoryScreen(
                viewModelFactory = viewModelFactory,
                navigateBack = { navController.popBackStack() },
                navigateToAnalysis = { }
            )
        }
    }
}

private fun NavGraphBuilder.addAccountGraph(
    viewModelFactory: ViewModelProvider.Factory,
    navController: NavHostController,
) {
    navigation(
        startDestination = ScreenRoute.Account.route,
        route = ScreenRoute.AccountGraph.route
    ) {
        composable(ScreenRoute.Account.route) {
            AccountScreen(
                viewModelFactory = viewModelFactory,
                navigateToEditAccount = { account ->
                    navController.navigate(
                        ScreenRoute.EditAccountRoute(
                            account.id,
                            account.name,
                            account.balance,
                            account.currency
                        )
                    )
                }
            )
        }
        composable<ScreenRoute.EditAccountRoute> {
            EditAccountScreen(
                navigateBack = { navController.popBackStack() },
                viewModelFactory = viewModelFactory
            )
        }
    }
}

private fun NavGraphBuilder.addArticlesGraph(
    viewModelFactory: ViewModelProvider.Factory
) {
    navigation(
        startDestination = ScreenRoute.Articles.route,
        route = ScreenRoute.ArticlesGraph.route
    ) {
        composable(ScreenRoute.Articles.route) {
            CategoriesScreen(viewModelFactory = viewModelFactory)
        }
    }
}

private fun NavGraphBuilder.addSettingsGraph() {
    navigation(
        startDestination = ScreenRoute.Settings.route,
        route = ScreenRoute.SettingsGraph.route
    ) {
        composable(ScreenRoute.Settings.route) {
            SettingsScreen()
        }
    }
}
