package com.example.incomes.domain.usecase.impl

import com.example.common.model.income.Income
import com.example.incomes.domain.repository.IncomesRepository
import com.example.incomes.domain.usecase.interfaces.GetIncomeByIdUseCase
import javax.inject.Inject

class GetIncomeByIdUseCaseImpl @Inject constructor(
    private val incomesRepository: IncomesRepository
) : GetIncomeByIdUseCase {
    override suspend operator fun invoke(incomeId: Int): Result<Income> {
        return incomesRepository.getIncomeById(incomeId)
    }
}
