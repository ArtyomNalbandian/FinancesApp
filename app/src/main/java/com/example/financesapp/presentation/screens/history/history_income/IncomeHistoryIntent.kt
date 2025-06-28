package com.example.financesapp.presentation.screens.history.history_income

sealed interface IncomeHistoryIntent {

    data class LoadHistory(
        val startDate: String,
        val endDate: String
    ) : IncomeHistoryIntent
}
