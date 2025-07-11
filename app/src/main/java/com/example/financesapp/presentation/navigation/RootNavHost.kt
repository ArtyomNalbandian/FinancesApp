package com.example.financesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.account.presentation.AccountScreenRoute
import com.example.account.presentation.accountScreen
import com.example.categories.presentation.CategoriesScreenRoute
import com.example.categories.presentation.categoriesScreen
import com.example.edit_account.presentation.EditAccountScreenRoute
import com.example.edit_account.presentation.editAccountScreen
import com.example.financesapp.presentation.screens.expenses.ExpensesScreen
import com.example.financesapp.presentation.screens.history.history_expenses.ExpensesHistoryScreen
import com.example.financesapp.presentation.screens.history.history_income.IncomeHistoryScreen
import com.example.financesapp.presentation.screens.income.IncomeScreen
import com.example.settings.navigation.SettingsScreenRoute
import com.example.settings.navigation.settingsScreen
import kotlinx.serialization.Serializable

@Composable
internal fun RootNavGraph(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.ExpensesGraph,
        modifier = modifier
    ) {
        addExpensesGraph(viewModelFactory, navController)
        addIncomeGraph(viewModelFactory, navController)
        addAccountGraph(viewModelFactory, navController)
        addCategoriesGraph()
        addSettingsGraph()
    }
}

@Serializable
data object ExpensesScreenRoute

@Serializable
data object ExpensesHistoryScreenRoute

private fun NavGraphBuilder.addExpensesGraph(
    viewModelFactory: ViewModelProvider.Factory,
    navController: NavHostController,
) {
    navigation<ScreenRoute.ExpensesGraph>(
        startDestination = ExpensesScreenRoute,
    ) {
        composable<ExpensesScreenRoute> {
            ExpensesScreen(
                viewModelFactory = viewModelFactory,
                navigateToHistory = { navController.navigate(ExpensesHistoryScreenRoute) }
            )
        }
        composable<ExpensesHistoryScreenRoute> {
            ExpensesHistoryScreen(
                viewModelFactory = viewModelFactory,
                navigateBack = { navController.popBackStack() },
                navigateToAnalysis = { }
            )
        }
    }
}

@Serializable
data object IncomeScreenRoute

@Serializable
data object IncomeHistoryScreenRoute

private fun NavGraphBuilder.addIncomeGraph(
    viewModelFactory: ViewModelProvider.Factory,
    navController: NavHostController,
) {
    navigation<ScreenRoute.IncomeGraph>(
        startDestination = IncomeScreenRoute,
    ) {
        composable<IncomeScreenRoute> {
            IncomeScreen(
                viewModelFactory = viewModelFactory,
                navigateToHistory = { navController.navigate(IncomeHistoryScreenRoute) }
            )
        }
        composable<IncomeHistoryScreenRoute> {
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
    navigation<ScreenRoute.AccountGraph>(
        startDestination = AccountScreenRoute,
    ) {
        accountScreen(
            navigateToEditAccount = {
                navController.navigate(EditAccountScreenRoute)
            }
        )
        editAccountScreen(
            navigateBack = { navController.popBackStack() }
        )
//        composable<EditAccountScreenRoute> {
//            EditAccountScreen(
//                navigateBack = { navController.popBackStack() },
//                viewModelFactory = viewModelFactory
//            )
//        }
    }
}

private fun NavGraphBuilder.addCategoriesGraph() {
    navigation<ScreenRoute.CategoriesGraph>(
        startDestination = CategoriesScreenRoute,
    ) {
        categoriesScreen()
    }
}

private fun NavGraphBuilder.addSettingsGraph() {
    navigation<ScreenRoute.SettingsGraph>(
        startDestination = SettingsScreenRoute,
    ) {
        settingsScreen()
    }
}
