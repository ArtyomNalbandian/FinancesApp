package com.example.financesapp.presentation.history.history_expenses

sealed interface ExpensesHistoryIntent {

    data class LoadHistory(
        val startDate: String,
        val endDate: String
    ) : ExpensesHistoryIntent
}
