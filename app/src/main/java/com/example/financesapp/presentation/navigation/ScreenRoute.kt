package com.example.financesapp.presentation.navigation

sealed class ScreenRoute(val route: String) {

    // Top level routes
    object ExpensesGraph : ScreenRoute("expenses_graph")
    object IncomeGraph : ScreenRoute("income_graph")
    object AccountGraph : ScreenRoute("account_graph")
    object ArticlesGraph : ScreenRoute("articles_graph")
    object SettingsGraph : ScreenRoute("settings_graph")

    // ExpensesGraph routes
    object Expenses : ScreenRoute("expenses")

    class History(historyType: String) : ScreenRoute(historyType + "_history")

    // IncomeGraph routes
    object Income : ScreenRoute("income")

    // AccountGraph routes
    object Account : ScreenRoute("account")

    // ArticlesGraph routes
    object Articles : ScreenRoute("articles")

    // SettingsGraph routes
    object Settings : ScreenRoute("settings")

}
