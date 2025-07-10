package com.example.financesapp.domain.usecases.interfaces

import com.example.common.model.expense.Expense

interface GetExpensesUseCase {

    suspend operator fun invoke(
        startDate: String? = null,
        endDate: String? = null
    ): List<Expense>
}
