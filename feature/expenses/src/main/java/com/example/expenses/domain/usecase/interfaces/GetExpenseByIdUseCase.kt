package com.example.expenses.domain.usecase.interfaces

import com.example.common.model.expense.Expense

interface GetExpenseByIdUseCase {

    suspend operator fun invoke(expenseId: Int): Result<Expense>
}
