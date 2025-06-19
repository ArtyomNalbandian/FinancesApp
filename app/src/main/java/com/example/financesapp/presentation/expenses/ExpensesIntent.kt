package com.example.financesapp.presentation.expenses

sealed interface ExpensesIntent {
    data class LoadExpenses(val accountId: Int, val startDate: String, val endDate: String) : ExpensesIntent
    data class OpenHistoryOfExpenses(val accountId: Int) : ExpensesIntent
    data object CreateExpense : ExpensesIntent
    data class EditExpense(val accountId: Int, val expenseId: Int) : ExpensesIntent
}