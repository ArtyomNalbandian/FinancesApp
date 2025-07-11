package com.example.expenses.domain.usecase.interfaces

interface CreateExpenseUseCase {

    suspend operator fun invoke(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?,
    ): Result<Unit>
}
