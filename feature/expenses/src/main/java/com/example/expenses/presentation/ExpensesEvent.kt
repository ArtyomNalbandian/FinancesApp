package com.example.expenses.presentation

internal sealed interface ExpensesEvent {

    data class ShowError(val message: String) : ExpensesEvent
}
