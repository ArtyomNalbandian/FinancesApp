package com.example.incomes.presentation.incomes_history

internal sealed interface IncomesHistoryIntent {

    data class LoadHistory(
        val startDate: String,
        val endDate: String
    ) : IncomesHistoryIntent
}
