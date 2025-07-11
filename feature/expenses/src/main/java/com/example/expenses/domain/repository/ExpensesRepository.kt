package com.example.expenses.domain.repository

import com.example.common.model.expense.Expense

interface ExpensesRepository {

    suspend fun getExpensesByPeriod(startDate: String?, endDate: String?): List<Expense>

    suspend fun createExpense(
        accountId: Int,
        categoryId: Int,
        amount: String,
        expenseDate: String,
        comment: String?
    ): Result<Unit>

    suspend fun getExpenseById(expenseId: Int): Result<Expense>

    suspend fun updateExpense(
        expenseId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        expenseDate: String,
        comment: String?,
    ): Result<Unit>
}
