package com.example.financesapp.presentation.navigation

import com.example.financesapp.domain.models.account.Account
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute(val route: String) {

    // Top level routes
    data object ExpensesGraph : ScreenRoute("expenses_graph")
    data object IncomeGraph : ScreenRoute("income_graph")
    data object AccountGraph : ScreenRoute("account_graph")
    data object ArticlesGraph : ScreenRoute("articles_graph")
    data object SettingsGraph : ScreenRoute("settings_graph")

    // ExpensesGraph routes
    data object Expenses : ScreenRoute("expenses")

    data class History(val historyType: String) : ScreenRoute(historyType + "_history")

    // IncomeGraph routes
    data object Income : ScreenRoute("income")

    // AccountGraph routes
    data object Account : ScreenRoute("account")
    @Serializable
    data class EditAccountRoute(
        val id: Int,
        val name: String,
        val sum: String,
        val currency: String,
    )

    // ArticlesGraph routes
    data object Articles : ScreenRoute("articles")

    // SettingsGraph routes
    data object Settings : ScreenRoute("settings")
}

//@Serializable
//data class  EditAccountRoute(
//    val account: Account,
//    val id: Int,
//    val name: String,
//    val sum: String,
//    val currency: String,
//)
