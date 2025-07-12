package com.example.expenses.domain.usecase.interfaces

interface UpdateExpenseUseCase {

    suspend operator fun invoke(
        expenseId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        expenseDate: String,
        comment: String?
    ): Result<Unit>
}
