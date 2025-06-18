package com.example.financesapp.presentation.expenses

sealed interface ExpensesIntent {
    data class OpenHistoryOfExpenses(val accountId: Int) : ExpensesIntent
    data object CreateExpense : ExpensesIntent
    data class EditExpense(val accountId: Int, val expenseId: Int) : ExpensesIntent
}