package com.example.financesapp.presentation.navigation

import com.example.financesapp.domain.models.account.Account
import kotlinx.serialization.Serializable

//@Serializable
sealed class ScreenRoute(val route: String) {

    // Top level routes
    data object ExpensesGraph : ScreenRoute("expenses_graph")
    data object IncomeGraph : ScreenRoute("income_graph")
    data object AccountGraph : ScreenRoute("account_graph")
    data object ArticlesGraph : ScreenRoute("articles_graph")
    data object SettingsGraph : ScreenRoute("settings_graph")

    // ExpensesGraph routes
//    @Serializable
    data object Expenses : ScreenRoute("expenses")

//    @Serializable
    data class History(val historyType: String) : ScreenRoute(historyType + "_history")

    // IncomeGraph routes
//    @Serializable
    data object Income : ScreenRoute("income")

    // AccountGraph routes
//    @Serializable
    data object Account : ScreenRoute("account")
//    @Serializable
//    data class EditAccount(val account: com.example.financesapp.domain.models.account.Account) : ScreenRoute("account_edit")

    // ArticlesGraph routes
//    @Serializable
    data object Articles : ScreenRoute("articles")

    // SettingsGraph routes
//    @Serializable
    data object Settings : ScreenRoute("settings")
}

//@Serializable
//data class EditRoute(
//    val account: Account
//)
