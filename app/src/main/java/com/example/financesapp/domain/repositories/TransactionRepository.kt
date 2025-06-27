package com.example.financesapp.domain.repositories

import com.example.financesapp.domain.models.expenses.Expense
import com.example.financesapp.domain.models.income.Income

interface TransactionRepository {

    suspend fun getExpensesByPeriod(startDate: String?, endDate: String?): List<Expense>
    suspend fun getIncomesByPeriod(startDate: String?, endDate: String?): List<Income>
}
