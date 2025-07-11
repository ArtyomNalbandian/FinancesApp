package com.example.expenses.domain.usecase.impl

import com.example.expenses.domain.repository.ExpensesRepository
import com.example.expenses.domain.usecase.interfaces.CreateExpenseUseCase
import javax.inject.Inject

class CreateExpenseUseCaseImpl @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : CreateExpenseUseCase {
    override suspend fun invoke(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit> {
        return expensesRepository.createExpense(
            accountId = accountId,
            categoryId = categoryId,
            amount = amount,
            expenseDate = transactionDate,
            comment = comment
        )
    }
}
