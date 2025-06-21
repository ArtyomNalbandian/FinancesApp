package com.example.financesapp.presentation.expenses

import com.example.financesapp.domain.expenses.Expense

sealed interface ExpensesState {
    data object Loading : ExpensesState
    data class Content(val expenses: List<Expense>, val total: String, val currency: String) :
        ExpensesState
    data class Error(val message: String) : ExpensesState
}
