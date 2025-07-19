package com.example.expenses.presentation.expenses_analysis

import com.example.common.model.expense.Expense

internal sealed class ExpensesAnalysisState {

    data object Loading : ExpensesAnalysisState()
    data class Content(
        val items: List<Expense>,
        val total: String
    ) : ExpensesAnalysisState()

    data class Error(val message: String) : ExpensesAnalysisState()
}
