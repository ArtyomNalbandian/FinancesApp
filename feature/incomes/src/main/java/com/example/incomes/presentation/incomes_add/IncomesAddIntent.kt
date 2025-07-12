package com.example.incomes.presentation.incomes_add

import com.example.common.model.account.Account
import com.example.common.model.category.Category
import java.time.LocalDate
import java.time.LocalTime

sealed interface IncomesAddIntent {
    data object LoadInitialData : IncomesAddIntent

    data object ToggleAccountDropdown : IncomesAddIntent
    data class SelectAccount(val account: Account) : IncomesAddIntent

    data object ToggleCategoryDropdown : IncomesAddIntent
    data class SelectCategory(val category: Category) : IncomesAddIntent

    data class AmountChanged(val amount: String) : IncomesAddIntent

    data object ShowDatePicker : IncomesAddIntent
    data object HideDatePicker : IncomesAddIntent
    data class DateSelected(val date: LocalDate) : IncomesAddIntent

    data object ShowTimePicker : IncomesAddIntent
    data object HideTimePicker : IncomesAddIntent
    data class TimeSelected(val time: LocalTime) : IncomesAddIntent

    data class CommentChanged(val comment: String) : IncomesAddIntent

    data object CreateIncome : IncomesAddIntent

    data object ClearError : IncomesAddIntent
}
