package com.example.financesapp.presentation.screens.history.history_expenses

sealed interface ExpensesHistoryIntent {

    data class LoadHistory(
        val startDate: String,
        val endDate: String
    ) : ExpensesHistoryIntent
}
