package com.example.financesapp.presentation.expenses

sealed interface ExpensesEvent {
    data object NavigateToCreateExpenseScreen : ExpensesEvent
    data object NavigateToExpensesHistoryScreen : ExpensesEvent
    data class NavigateToEditExpenseScreen(val expenseId: String) : ExpensesEvent
    data class ShowError(val message: String) : ExpensesEvent
}