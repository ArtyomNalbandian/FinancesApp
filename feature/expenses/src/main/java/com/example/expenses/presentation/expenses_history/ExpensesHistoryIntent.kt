package com.example.expenses.presentation.expenses_history

internal sealed interface ExpensesHistoryIntent {

    data class LoadHistory(
        val startDate: String,
        val endDate: String
    ) : ExpensesHistoryIntent
}
