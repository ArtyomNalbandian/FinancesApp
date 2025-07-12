package com.example.expenses.domain.usecase.impl

import com.example.expenses.domain.repository.ExpensesRepository
import com.example.expenses.domain.usecase.interfaces.UpdateExpenseUseCase
import javax.inject.Inject

class UpdateExpenseUseCaseImpl @Inject constructor(
    private val expensesRepository: ExpensesRepository
): UpdateExpenseUseCase {
    override suspend operator fun invoke(
        expenseId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        expenseDate: String,
        comment: String?
    ): Result<Unit> {
        return expensesRepository.updateExpense(
            expenseId = expenseId,
            accountId = accountId,
            categoryId = categoryId,
            amount = amount,
            expenseDate = expenseDate,
            comment = comment
        )
    }
}
