package com.example.financesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.example.account.presentation.AccountScreenRoute
import com.example.account.presentation.accountScreen
import com.example.categories.presentation.CategoriesScreenRoute
import com.example.categories.presentation.categoriesScreen
import com.example.edit_account.presentation.EditAccountScreenRoute
import com.example.edit_account.presentation.editAccountScreen
import com.example.expenses.presentation.expenses.ExpensesScreenRoute
import com.example.expenses.presentation.expenses.expensesScreen
import com.example.expenses.presentation.expenses_history.ExpensesHistoryScreenRoute
import com.example.expenses.presentation.expenses_history.expensesHistoryScreen
import com.example.incomes.presentation.incomes.IncomesScreenRoute
import com.example.incomes.presentation.incomes.incomesScreen
import com.example.incomes.presentation.incomes_history.IncomesHistoryScreenRoute
import com.example.incomes.presentation.incomes_history.incomesHistoryScreen
import com.example.settings.navigation.SettingsScreenRoute
import com.example.settings.navigation.settingsScreen

@Composable
internal fun RootNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.ExpensesGraph,
        modifier = modifier
    ) {
        addExpensesGraph(navController)
        addIncomeGraph(navController)
        addAccountGraph(navController)
        addCategoriesGraph()
        addSettingsGraph()
    }
}

private fun NavGraphBuilder.addExpensesGraph(
    navController: NavHostController
) {
    navigation<ScreenRoute.ExpensesGraph>(
        startDestination = ExpensesScreenRoute,
    ) {
        expensesScreen(
            navigateToHistory = { navController.navigate(ExpensesHistoryScreenRoute) }
        )
        expensesHistoryScreen(
            navigateBack = { navController.popBackStack() },
            navigateToAnalysis = { }
        )
    }
}

private fun NavGraphBuilder.addIncomeGraph(
    navController: NavHostController
) {
    navigation<ScreenRoute.IncomeGraph>(
        startDestination = IncomesScreenRoute,
    ) {
        incomesScreen(
            navigateToHistory = { navController.navigate(IncomesHistoryScreenRoute) }
        )
        incomesHistoryScreen(
            navigateBack = { navController.popBackStack() },
            navigateToAnalysis = { }
        )
    }
}

private fun NavGraphBuilder.addAccountGraph(
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
