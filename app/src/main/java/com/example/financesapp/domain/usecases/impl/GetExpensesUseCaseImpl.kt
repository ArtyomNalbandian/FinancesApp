package com.example.financesapp.domain.usecases.impl

import com.example.common.model.expense.Expense
import com.example.financesapp.domain.repositories.TransactionRepository
import com.example.financesapp.domain.usecases.interfaces.GetExpensesUseCase
import javax.inject.Inject

/**
 * Реализация [GetExpensesUseCase] для получения списка расходов за период.
 * Инкапсулирует бизнес-логику получения отфильтрованных по датам расходов,
 * делегируя вызов к [TransactionRepository].
 * @property transactionRepository Источник данных о транзакциях [TransactionRepository]
 * @constructor Создает UseCase для работы с расходами
 * @param transactionRepository Репозиторий транзакций (внедряется через DI)
 */
class GetExpensesUseCaseImpl @Inject constructor(
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
