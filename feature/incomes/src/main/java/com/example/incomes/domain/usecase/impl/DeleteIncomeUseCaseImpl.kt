package com.example.incomes.domain.usecase.impl

import com.example.incomes.domain.repository.IncomesRepository
import com.example.incomes.domain.usecase.interfaces.DeleteIncomeUseCase
import javax.inject.Inject

class DeleteIncomeUseCaseImpl @Inject constructor(
    private val expensesRepository: IncomesRepository
) : DeleteIncomeUseCase {
    override suspend operator fun invoke(expenseId: Int): Result<Unit> {
        return expensesRepository.deleteIncome(expenseId)
    }
}
