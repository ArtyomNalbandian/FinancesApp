package com.example.incomes.domain.usecase.interfaces

interface CreateIncomeUseCase {

    suspend operator fun invoke(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<Unit>
}
