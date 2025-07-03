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
import com.example.financesapp.presentation.screens.categories.CategoriesScreen
import com.example.financesapp.presentation.screens.expenses.ExpensesScreen
import com.example.financesapp.presentation.screens.history.history_expenses.ExpensesHistoryScreen
import com.example.financesapp.presentation.screens.history.history_income.IncomeHistoryScreen
import com.example.financesapp.presentation.screens.income.IncomeScreen
import com.example.financesapp.presentation.screens.settings.SettingsScreen

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
//                    navController.navigate(ScreenRoute.EditAccount(account).route)
                }
            )
        }
//        composable<EditRoute> {
//            val args = it.toRoute<ScreenRoute.EditAccount>()
//            EditAccountScreen(
//                account = args.account,
//                viewModelFactory = viewModelFactory,
//                navigateBack = { navController.popBackStack() }
//            )
//        }
//        composable(
//            arguments = listOf(navArgument("account") { type = NavType.StringType }),
//            route = ScreenRoute.EditAccount().route,
//        ) { backStackEntry ->
//            val accountId = backStackEntry.arguments?.getString("accountId") ?: return@composable
//                EditAccountScreen(
//                    accountId = accountId,
//                    viewModelFactory = viewModelFactory,
//                    navigateBack = { navController.popBackStack() }
//                )
//        }
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
            CategoriesScreen(
                viewModelFactory = viewModelFactory
            )
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
