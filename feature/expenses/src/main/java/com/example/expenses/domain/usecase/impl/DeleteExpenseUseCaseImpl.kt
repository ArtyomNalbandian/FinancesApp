package com.example.expenses.domain.usecase.impl

import com.example.expenses.domain.repository.ExpensesRepository
import com.example.expenses.domain.usecase.interfaces.DeleteExpenseUseCase
import javax.inject.Inject

class DeleteExpenseUseCaseImpl @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : DeleteExpenseUseCase {
    override suspend operator fun invoke(expenseId: Int): Result<Unit> {
        return expensesRepository.deleteExpense(expenseId)
    }
}
