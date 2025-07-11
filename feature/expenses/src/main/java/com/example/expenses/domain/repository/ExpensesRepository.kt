package com.example.expenses.domain.repository

import com.example.common.model.expense.Expense

interface ExpensesRepository {

    suspend fun getExpensesByPeriod(startDate: String?, endDate: String?): List<Expense>
}
