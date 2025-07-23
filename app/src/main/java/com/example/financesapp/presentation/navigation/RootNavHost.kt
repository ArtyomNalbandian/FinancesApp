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
import com.example.expenses.presentation.expenses_add.ExpensesAddScreenRoute
import com.example.expenses.presentation.expenses_add.expensesAddScreen
import com.example.expenses.presentation.expenses_analysis.ExpensesAnalysisScreenRoute
import com.example.expenses.presentation.expenses_analysis.expensesAnalysisScreen
import com.example.expenses.presentation.expenses_edit.expensesEditScreen
import com.example.expenses.presentation.expenses_history.ExpensesHistoryScreenRoute
import com.example.expenses.presentation.expenses_history.expensesHistoryScreen
import com.example.incomes.presentation.incomes.IncomesScreenRoute
import com.example.incomes.presentation.incomes.incomesScreen
import com.example.incomes.presentation.incomes_add.IncomesAddScreenRoute
import com.example.incomes.presentation.incomes_add.incomesAddScreen
import com.example.incomes.presentation.incomes_analysis.IncomesAnalysisScreenRoute
import com.example.incomes.presentation.incomes_analysis.incomesAnalysisScreen
import com.example.incomes.presentation.incomes_edit.incomesEditScreen
import com.example.incomes.presentation.incomes_history.IncomesHistoryScreenRoute
import com.example.incomes.presentation.incomes_history.incomesHistoryScreen
import com.example.settings.navigation.SettingsScreenRoute
import com.example.settings.navigation.settingsScreen
import com.example.settings.navigation.ColorPickerScreenRoute
import com.example.settings.navigation.HapticsSettingsScreenRoute
import com.example.settings.navigation.PinCodeScreenRoute
import com.example.settings.navigation.SyncFrequencyScreenRoute
import com.example.settings.navigation.LanguageSwitchScreenRoute
import com.example.settings.navigation.AppInfoScreenRoute
import com.example.settings.navigation.appInfoScreen
import com.example.settings.navigation.colorPickerScreen
import com.example.settings.navigation.hapticsSettingsScreen
import com.example.settings.navigation.languageSwitchScreen
import com.example.settings.navigation.pinCodeScreen
import com.example.settings.navigation.syncFrequencyScreen

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
        addSettingsGraph(navController)
    }
}

private fun NavGraphBuilder.addExpensesGraph(
    navController: NavHostController
) {
    navigation<ScreenRoute.ExpensesGraph>(
        startDestination = ExpensesScreenRoute,
    ) {
        expensesScreen(
            navigateToHistory = { navController.navigate(ExpensesHistoryScreenRoute) },
            navigateToAddExpense = { navController.navigate(ExpensesAddScreenRoute) },
            navigateToEditExpense = { navController.navigate("expenses_edit/$it") }
        )
        expensesHistoryScreen(
            navigateBack = { navController.popBackStack() },
            navigateToAnalysis = { navController.navigate(ExpensesAnalysisScreenRoute) }
        )
        expensesAnalysisScreen(
            navigateBack = { navController.popBackStack() }
        )
        expensesAddScreen(
            navigateBack = { navController.popBackStack() },
        )
        expensesEditScreen(
            onNavigateBack = { navController.popBackStack() }
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
            navigateToHistory = { navController.navigate(IncomesHistoryScreenRoute) },
            navigateToAddIncome = { navController.navigate(IncomesAddScreenRoute) },
            navigateToEditIncome = { navController.navigate("incomes_edit/$it") }
        )
        incomesHistoryScreen(
            navigateBack = { navController.popBackStack() },
            navigateToAnalysis = { navController.navigate(IncomesAnalysisScreenRoute) }
        )
        incomesAnalysisScreen(
            navigateBack = { navController.popBackStack() }
        )
        incomesAddScreen(
            navigateBack = { navController.popBackStack() },
        )
        incomesEditScreen(
            onNavigateBack = { navController.popBackStack() }
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

private fun NavGraphBuilder.addSettingsGraph(navController: NavHostController) {
    navigation<ScreenRoute.SettingsGraph>(
        startDestination = SettingsScreenRoute,
    ) {
        settingsScreen(
            navigateToColorPicker = { navController.navigate(ColorPickerScreenRoute) },
            navigateToHaptics = { navController.navigate(HapticsSettingsScreenRoute) },
            navigateToPinCode = { navController.navigate(PinCodeScreenRoute) },
            navigateToSyncFrequency = { navController.navigate(SyncFrequencyScreenRoute) },
            navigateToLanguageSwitch = { navController.navigate(LanguageSwitchScreenRoute) },
            navigateToAppInfo = { navController.navigate(AppInfoScreenRoute) }
        )
        colorPickerScreen()
        hapticsSettingsScreen()
        pinCodeScreen()
        syncFrequencyScreen()
        languageSwitchScreen()
        appInfoScreen()
    }
}
