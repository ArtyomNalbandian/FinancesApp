package com.example.financesapp.presentation.history.history_income

import com.example.financesapp.domain.income.Income

sealed interface IncomeHistoryState {
    data object Loading: IncomeHistoryState
    data class Content(
        val items: List<Income>,
        val total: String
    ): IncomeHistoryState
    data class Error(val message: String): IncomeHistoryState
}