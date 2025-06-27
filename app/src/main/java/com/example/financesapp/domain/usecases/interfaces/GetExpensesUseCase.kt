package com.example.financesapp.domain.usecases.interfaces

import com.example.financesapp.domain.models.expenses.Expense

interface GetExpensesUseCase {

    suspend operator fun invoke(
        startDate: String? = null,
        endDate: String? = null
    ): List<Expense>
}
