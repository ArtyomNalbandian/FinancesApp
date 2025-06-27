package com.example.financesapp.presentation.screens.expenses

sealed interface ExpensesIntent {
    data class LoadExpenses(val startDate: String, val endDate: String) : ExpensesIntent
//    data object OpenHistoryOfExpenses/*(val accountId: Int)*/ : ExpensesIntent
//    data object CreateExpense : ExpensesIntent
//    data class EditExpense(/*val accountId: Int, */val expenseId: Int) : ExpensesIntent
}