package com.example.incomes.domain.repository

import com.example.common.model.income.Income

interface IncomesRepository {

    suspend fun getIncomesByPeriod(startDate: String?, endDate: String?): List<Income>

    suspend fun createIncome(
        accountId: Int,
        categoryId: Int,
        amount: String,
        incomeDate: String,
        comment: String?
    ): Result<Unit>

    suspend fun getIncomeById(incomeId: Int): Result<Income>

    suspend fun updateIncome(
        incomeId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        incomeDate: String,
        comment: String?,
    ): Result<Unit>

    suspend fun deleteIncome(incomeId: Int): Result<Unit>
}
