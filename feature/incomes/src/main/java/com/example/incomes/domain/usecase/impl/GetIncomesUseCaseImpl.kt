package com.example.incomes.domain.usecase.impl

import com.example.common.model.income.Income
import com.example.incomes.domain.repository.IncomesRepository
import com.example.incomes.domain.usecase.interfaces.GetIncomesUseCase
import javax.inject.Inject

/**
 * Реализация [GetIncomesUseCase] для получения списка доходов за период.
 * Предоставляет стандартизированный интерфейс для получения доходов,
 * используя [IncomesRepository] как источник данных.
 * @property incomesRepository Источник данных о транзакциях [IncomesRepository]
 * @constructor Создает UseCase для работы с доходами
 * @param incomesRepository Репозиторий транзакций (внедряется через DI)
 */
class GetIncomesUseCaseImpl @Inject constructor(
    private val incomesRepository: IncomesRepository,
) : GetIncomesUseCase {

    override suspend operator fun invoke(
        startDate: String?,
        endDate: String?
    ): List<Income> {
        return incomesRepository.getIncomesByPeriod(
            startDate = startDate,
            endDate = endDate
        )
    }
}
