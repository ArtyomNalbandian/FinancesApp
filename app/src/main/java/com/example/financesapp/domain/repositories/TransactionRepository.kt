package com.example.financesapp.domain.repositories

import com.example.common.model.expense.Expense
import com.example.common.model.income.Income

interface TransactionRepository {

    suspend fun getExpensesByPeriod(startDate: String?, endDate: String?): List<Expense>
    suspend fun getIncomesByPeriod(startDate: String?, endDate: String?): List<Income>
}
