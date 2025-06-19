package com.example.financesapp.domain.usecase.impl

import com.example.financesapp.data.mapper.toIncome
import com.example.financesapp.domain.income.Income
import com.example.financesapp.domain.repository.RemoteDataSourceRepository
import com.example.financesapp.domain.usecase.GetIncomesUseCase

class GetIncomesUseCaseImpl(
    private val remoteDataSourceRepository: RemoteDataSourceRepository
) : GetIncomesUseCase {

    override suspend operator fun invoke(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Income> {
        val transactions = remoteDataSourceRepository.getTransactionsByPeriod(
            accountId = accountId,
            startDate = startDate,
            endDate = endDate
        )

        return transactions
            .filter { it.category.isIncome }
            .map { it.toIncome() }
    }

}