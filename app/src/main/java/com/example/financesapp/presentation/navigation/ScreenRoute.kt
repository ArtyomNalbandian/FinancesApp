package com.example.financesapp.presentation.navigation

sealed class ScreenRoute(val route: String) {

    // Top level routes
    data object ExpensesGraph : ScreenRoute("expenses_graph")
    data object IncomeGraph : ScreenRoute("income_graph")
    data object AccountGraph : ScreenRoute("account_graph")
    data object ArticlesGraph : ScreenRoute("articles_graph")
    data object SettingsGraph : ScreenRoute("settings_graph")

    // ExpensesGraph routes
    data object Expenses : ScreenRoute("expenses")

    class History(historyType: String) : ScreenRoute(historyType + "_history")

    // IncomeGraph routes
    data object Income : ScreenRoute("income")

    // AccountGraph routes
    data object Account : ScreenRoute("account")
    data class EditAccount(val accountId: String): ScreenRoute("account/$accountId")


    // ArticlesGraph routes
    data object Articles : ScreenRoute("articles")

    // SettingsGraph routes
    data object Settings : ScreenRoute("settings")
}
