package com.example.expenses.presentation.expenses_history

import com.example.common.model.expense.Expense

internal sealed class ExpensesHistoryState {

    data object Loading : ExpensesHistoryState()
    data class Content(
        val items: List<Expense>,
        val total: String
    ) : ExpensesHistoryState()

    data class Error(val message: String) : ExpensesHistoryState()
}
