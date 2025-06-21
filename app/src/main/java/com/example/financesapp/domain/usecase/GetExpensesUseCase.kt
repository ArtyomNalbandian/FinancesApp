package com.example.financesapp.domain.usecase

import com.example.financesapp.domain.expenses.Expense

interface GetExpensesUseCase {

    suspend operator fun invoke(
        startDate: String? = null,
        endDate: String? = null
    ): List<Expense>

}