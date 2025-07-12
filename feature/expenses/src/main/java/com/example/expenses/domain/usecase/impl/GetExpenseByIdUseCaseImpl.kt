package com.example.expenses.domain.usecase.impl

import com.example.common.model.expense.Expense
import com.example.expenses.domain.repository.ExpensesRepository
import com.example.expenses.domain.usecase.interfaces.GetExpenseByIdUseCase
import javax.inject.Inject

class GetExpenseByIdUseCaseImpl @Inject constructor(
    private val expensesRepository: ExpensesRepository
): GetExpenseByIdUseCase {
    override suspend operator fun invoke(expenseId: Int): Result<Expense> {
        return expensesRepository.getExpenseById(expenseId)
    }
}
