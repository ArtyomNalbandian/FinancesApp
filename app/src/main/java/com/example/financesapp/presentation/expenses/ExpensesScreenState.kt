package com.example.financesapp.presentation.expenses

import com.example.financesapp.domain.expenses.Expense

sealed interface ExpensesScreenState {
    data object Loading : ExpensesScreenState
    data class Content(val expenses: List<Expense>, val total: String, val currency: String) :
        ExpensesScreenState
    data class Error(val message: String) : ExpensesScreenState
}
