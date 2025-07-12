package com.example.expenses.presentation.expenses_edit

import com.example.common.model.account.Account
import com.example.common.model.category.Category
import java.time.LocalDate
import java.time.LocalTime

sealed class ExpensesEditIntent {
    data class LoadTransaction(val expenseId: Int) : ExpensesEditIntent()
    data class SelectAccount(val account: Account) : ExpensesEditIntent()
    data class SelectCategory(val category: Category) : ExpensesEditIntent()
    data class AmountChanged(val amount: String) : ExpensesEditIntent()
    data class DateSelected(val date: LocalDate) : ExpensesEditIntent()
    data class TimeSelected(val time: LocalTime) : ExpensesEditIntent()
    data class CommentChanged(val comment: String) : ExpensesEditIntent()
    object UpdateExpense : ExpensesEditIntent()
    object DeleteExpense : ExpensesEditIntent()
    object ClearError : ExpensesEditIntent()
}

//sealed class ExpensesEditIntent {
//    data object LoadTransaction : ExpensesEditIntent()
//    data class SelectAccount(val account: Account) : ExpensesEditIntent()
//    data class SelectCategory(val category: Category) : ExpensesEditIntent()
//    data class AmountChanged(val amount: String) : ExpensesEditIntent()
//    data class DateSelected(val date: LocalDate) : ExpensesEditIntent()
//    data class TimeSelected(val time: LocalTime) : ExpensesEditIntent()
//    data class CommentChanged(val comment: String) : ExpensesEditIntent()
//    data object UpdateExpense : ExpensesEditIntent()
//    data object DeleteExpense : ExpensesEditIntent()
//    data object ClearError : ExpensesEditIntent()
//}
