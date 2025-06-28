package com.example.financesapp.presentation.history.history_expenses

import com.example.financesapp.domain.models.expenses.Expense

sealed class ExpensesHistoryState {

    data object Loading : ExpensesHistoryState()
    data class Content(
        val items: List<Expense>,
        val total: String
    ) : ExpensesHistoryState()

    data class Error(val message: String) : ExpensesHistoryState()
}
