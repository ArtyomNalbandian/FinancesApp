package com.example.expenses.presentation.expenses_add

import com.example.common.model.account.Account
import com.example.common.model.category.Category
import java.time.LocalDate
import java.time.LocalTime

//internal sealed interface ExpensesAddIntent {
//
//    data class AddExpense(
//        val startDate: String,
//        val endDate: String
//    ) : ExpensesAddIntent
//}

sealed interface ExpensesAddIntent {
    data object LoadInitialData : ExpensesAddIntent

    // Account selection
    data object ToggleAccountDropdown : ExpensesAddIntent
    data class SelectAccount(val account: Account) : ExpensesAddIntent

    // Category selection
    data object ToggleCategoryDropdown : ExpensesAddIntent
    data class SelectCategory(val category: Category) : ExpensesAddIntent

    // Amount input
    data class AmountChanged(val amount: String) : ExpensesAddIntent

    // Date/Time selection
    data object ShowDatePicker : ExpensesAddIntent
    data object HideDatePicker : ExpensesAddIntent
    data class DateSelected(val date: LocalDate) : ExpensesAddIntent

    data object ShowTimePicker : ExpensesAddIntent
    data object HideTimePicker : ExpensesAddIntent
    data class TimeSelected(val time: LocalTime) : ExpensesAddIntent

    // Comment input
    data class CommentChanged(val comment: String) : ExpensesAddIntent

    // Transaction creation
    data object CreateTransaction : ExpensesAddIntent

    // Error handling
    data object ClearError : ExpensesAddIntent
}
