package com.example.expenses.presentation.expenses_add

import com.example.common.model.account.Account
import com.example.common.model.category.Category
import java.time.LocalDate
import java.time.LocalTime

sealed interface ExpensesAddIntent {
    data object LoadInitialData : ExpensesAddIntent

    data object ToggleAccountDropdown : ExpensesAddIntent
    data class SelectAccount(val account: Account) : ExpensesAddIntent

    data object ToggleCategoryDropdown : ExpensesAddIntent
    data class SelectCategory(val category: Category) : ExpensesAddIntent

    data class AmountChanged(val amount: String) : ExpensesAddIntent

    data object ShowDatePicker : ExpensesAddIntent
    data object HideDatePicker : ExpensesAddIntent
    data class DateSelected(val date: LocalDate) : ExpensesAddIntent

    data object ShowTimePicker : ExpensesAddIntent
    data object HideTimePicker : ExpensesAddIntent
    data class TimeSelected(val time: LocalTime) : ExpensesAddIntent

    data class CommentChanged(val comment: String) : ExpensesAddIntent

    data object CreateExpense : ExpensesAddIntent

    data object ClearError : ExpensesAddIntent
}
