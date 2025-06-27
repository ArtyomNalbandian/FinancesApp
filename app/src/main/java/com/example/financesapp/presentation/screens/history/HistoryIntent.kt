package com.example.financesapp.presentation.screens.history

sealed interface HistoryIntent {
    data class LoadHistory(
        val startDate: String,
        val endDate: String
    ): HistoryIntent
}