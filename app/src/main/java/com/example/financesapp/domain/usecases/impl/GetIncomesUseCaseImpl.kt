package com.example.financesapp.domain.usecases.impl

import com.example.common.model.income.Income
import com.example.financesapp.domain.repositories.TransactionRepository
import com.example.financesapp.domain.usecases.interfaces.GetIncomesUseCase
import javax.inject.Inject

/**
 * Реализация [GetIncomesUseCase] для получения списка доходов за период.
 * Предоставляет стандартизированный интерфейс для получения доходов,
 * используя [TransactionRepository] как источник данных.
 * @property transactionRepository Источник данных о транзакциях [TransactionRepository]
 * @constructor Создает UseCase для работы с доходами
 * @param transactionRepository Репозиторий транзакций (внедряется через DI)
 */
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
