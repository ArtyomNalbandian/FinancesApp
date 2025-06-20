package com.example.financesapp.presentation.history

import com.example.financesapp.domain.expenses.Expense

sealed class ExpensesHistoryState {
    object Loading: ExpensesHistoryState()
    data class Success(
        val items: List<Expense>,
        val total: String
    ): ExpensesHistoryState()
    data class Error(val message: String): ExpensesHistoryState()
}