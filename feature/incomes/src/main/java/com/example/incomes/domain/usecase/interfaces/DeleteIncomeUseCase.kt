package com.example.incomes.domain.usecase.interfaces

interface DeleteIncomeUseCase {

    suspend operator fun invoke(expenseId: Int): Result<Unit>
}
