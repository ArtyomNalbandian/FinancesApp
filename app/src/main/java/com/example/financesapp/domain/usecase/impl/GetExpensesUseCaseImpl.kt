package com.example.financesapp.domain.usecase.impl

import com.example.financesapp.domain.models.expenses.Expense
import com.example.financesapp.domain.repositories.TransactionRepository
import com.example.financesapp.domain.usecase.GetExpensesUseCase

class GetExpensesUseCaseImpl(
    private val transactionRepository: TransactionRepository
) : GetExpensesUseCase {

    override suspend operator fun invoke(
        startDate: String?,
        endDate: String?
    ): List<Expense> {
        return transactionRepository.getExpensesByPeriod(
            startDate = startDate,
            endDate = endDate
        )
    }
}
