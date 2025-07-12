package com.example.incomes.domain.usecase.interfaces

interface UpdateIncomeUseCase {

    suspend operator fun invoke(
        expenseId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        expenseDate: String,
        comment: String?
    ): Result<Unit>
}
