package com.example.incomes.domain.usecase.impl

import com.example.incomes.domain.repository.IncomesRepository
import com.example.incomes.domain.usecase.interfaces.CreateIncomeUseCase
import javax.inject.Inject

class CreateIncomeUseCaseImpl @Inject constructor(
    private val incomesRepository: IncomesRepository
) : CreateIncomeUseCase {
    override suspend fun invoke(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit> {
        return incomesRepository.createIncome(
            accountId = accountId,
            categoryId = categoryId,
            amount = amount,
            incomeDate = transactionDate,
            comment = comment
        )
    }
}
