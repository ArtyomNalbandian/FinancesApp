package com.example.incomes.domain.usecase.impl

import com.example.incomes.domain.repository.IncomesRepository
import com.example.incomes.domain.usecase.interfaces.UpdateIncomeUseCase
import javax.inject.Inject

class UpdateIncomeUseCaseImpl @Inject constructor(
    private val expensesRepository: IncomesRepository
) : UpdateIncomeUseCase {
    override suspend operator fun invoke(
        expenseId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        expenseDate: String,
        comment: String?
    ): Result<Unit> {
        return expensesRepository.updateIncome(
            incomeId = expenseId,
            accountId = accountId,
            categoryId = categoryId,
            amount = amount,
            incomeDate = expenseDate,
            comment = comment
        )
    }
}
