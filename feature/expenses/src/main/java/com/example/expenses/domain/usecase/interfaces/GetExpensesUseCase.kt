package com.example.expenses.domain.usecase.interfaces

import com.example.common.model.expense.Expense

interface GetExpensesUseCase {

    suspend operator fun invoke(
        startDate: String? = null,
        endDate: String? = null
    ): List<Expense>
}
