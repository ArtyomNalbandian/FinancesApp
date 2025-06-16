package com.example.financesapp.presentation.navigation

sealed class Screen(val route: String) {

    // Top level routes
    object ExpensesGraph : Screen("expenses_graph")
    object IncomeGraph : Screen("income_graph")
    object AccountGraph : Screen("account_graph")
    object ArticlesGraph : Screen("articles_graph")
    object SettingsGraph : Screen("settings_graph")

    // ExpensesGraph routes
    object Expenses : Screen("expenses")
//    object ExpensesHistory : Screen("expenses_history")
    object AddExpense : Screen("add_expense")

    class History(historyType: String) : Screen(historyType + "_history")
    class Analysis(analysisType: String) : Screen(analysisType + "_analysis")

    // IncomeGraph routes
    object Income : Screen("income")
//    object IncomeHistory : Screen("income_history")
    object AddIncome : Screen("add_income")

    // AccountGraph routes
    object Account : Screen("account")
    object AddAccount : Screen("add_account")

    // ArticlesGraph routes
    object Articles : Screen("articles")

    // SettingsGraph routes
    object Settings : Screen("settings")

}