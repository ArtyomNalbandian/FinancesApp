package com.example.incomes.domain.repository

import com.example.common.model.income.Income

interface IncomesRepository {

    suspend fun getIncomesByPeriod(startDate: String?, endDate: String?): List<Income>
}
