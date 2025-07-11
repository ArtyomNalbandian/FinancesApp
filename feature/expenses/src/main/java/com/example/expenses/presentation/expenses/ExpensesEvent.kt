package com.example.expenses.presentation.expenses

internal sealed interface ExpensesEvent {

    data class ShowError(val message: String) : ExpensesEvent
}
