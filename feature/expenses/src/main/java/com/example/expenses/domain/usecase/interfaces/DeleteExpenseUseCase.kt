package com.example.expenses.domain.usecase.interfaces

interface DeleteExpenseUseCase {

    suspend operator fun invoke(expenseId: Int): Result<Unit>
}
