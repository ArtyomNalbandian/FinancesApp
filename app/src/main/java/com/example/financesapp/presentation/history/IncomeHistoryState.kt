package com.example.financesapp.presentation.history

import com.example.financesapp.domain.income.Income

sealed class IncomeHistoryState {
    object Loading: IncomeHistoryState()
    data class Success(
        val items: List<Income>,
        val total: String
    ): IncomeHistoryState()
    data class Error(val message: String): IncomeHistoryState()
}