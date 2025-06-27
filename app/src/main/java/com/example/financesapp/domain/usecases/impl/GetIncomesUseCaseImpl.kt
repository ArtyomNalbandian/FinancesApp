package com.example.financesapp.domain.usecases.impl

import com.example.financesapp.domain.models.income.Income
import com.example.financesapp.domain.repositories.TransactionRepository
import com.example.financesapp.domain.usecases.interfaces.GetIncomesUseCase
import javax.inject.Inject

class GetIncomesUseCaseImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
) : GetIncomesUseCase {

    override suspend operator fun invoke(
        startDate: String?,
        endDate: String?
    ): List<Income> {
        return transactionRepository.getIncomesByPeriod(
            startDate = startDate,
            endDate = endDate
        )
    }
}
