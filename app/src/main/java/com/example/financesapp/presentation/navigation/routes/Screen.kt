package com.example.financesapp.presentation.navigation.routes

sealed class Screen(val route: String) {

    // Top level routes
    object ExpensesGraph : Screen("expenses_graph")
    object IncomeGraph : Screen("income_graph")
    object AccountGraph : Screen("account_graph")
    object ArticlesGraph : Screen("articles_graph")
    object SettingsGraph : Screen("settings_graph")

    // ExpensesGraph routes
    object Expenses : Screen("expenses")

    // IncomeGraph routes
    object Income : Screen("income")

    // AccountGraph routes
    object Account : Screen("account")

    // ArticlesGraph routes
    object Articles : Screen("articles")

    // SettingsGraph routes
    object Settings : Screen("settings")

}