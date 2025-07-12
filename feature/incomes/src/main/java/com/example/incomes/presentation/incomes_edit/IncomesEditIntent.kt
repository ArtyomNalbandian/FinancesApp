package com.example.incomes.presentation.incomes_edit

import com.example.common.model.account.Account
import com.example.common.model.category.Category
import java.time.LocalDate
import java.time.LocalTime

sealed class IncomesEditIntent {
    data class LoadTransaction(val expenseId: Int) : IncomesEditIntent()
    data class SelectAccount(val account: Account) : IncomesEditIntent()
    data class SelectCategory(val category: Category) : IncomesEditIntent()
    data class AmountChanged(val amount: String) : IncomesEditIntent()
    data class DateSelected(val date: LocalDate) : IncomesEditIntent()
    data class TimeSelected(val time: LocalTime) : IncomesEditIntent()
    data class CommentChanged(val comment: String) : IncomesEditIntent()
    data object UpdateIncome : IncomesEditIntent()
    data object DeleteIncome : IncomesEditIntent()
    data object ClearError : IncomesEditIntent()
}
