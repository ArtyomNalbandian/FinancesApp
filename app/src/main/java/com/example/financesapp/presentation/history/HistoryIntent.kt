package com.example.financesapp.presentation.history

sealed interface HistoryIntent {
    data class LoadHistory(
        val startDate: String,
        val endDate: String
    ): HistoryIntent
}