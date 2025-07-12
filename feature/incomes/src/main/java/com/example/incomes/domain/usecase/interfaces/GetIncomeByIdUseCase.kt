package com.example.incomes.domain.usecase.interfaces

import com.example.common.model.income.Income

interface GetIncomeByIdUseCase {

    suspend operator fun invoke(incomeId: Int): Result<Income>
}
