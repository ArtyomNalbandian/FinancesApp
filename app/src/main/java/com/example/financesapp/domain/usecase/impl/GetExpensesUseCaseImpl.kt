package com.example.financesapp.domain.usecase.impl

import com.example.financesapp.data.mapper.toExpense
import com.example.financesapp.domain.expenses.Expense
import com.example.financesapp.domain.repository.RemoteDataSourceRepository
import com.example.financesapp.domain.usecase.GetExpensesUseCase

class GetExpensesUseCaseImpl(
    private val remoteDataSourceRepository: RemoteDataSourceRepository
) : GetExpensesUseCase {

    override suspend operator fun invoke(
        startDate: String?,
        endDate: String?
    ): List<Expense> {
        val transactions = remoteDataSourceRepository.getTransactionsByPeriod(
            startDate = startDate,
            endDate = endDate
        )
        return transactions
            .filter { !it.category.isIncome }
            .map { it.toExpense() }
    }
}
