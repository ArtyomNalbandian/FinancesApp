package com.example.expenses.domain.usecase.impl

import com.example.common.model.expense.Expense
import com.example.expenses.domain.repository.ExpensesRepository
import com.example.expenses.domain.usecase.interfaces.GetExpensesUseCase
import javax.inject.Inject

/**
 * Реализация [GetExpensesUseCase] для получения списка расходов за период.
 * Инкапсулирует бизнес-логику получения отфильтрованных по датам расходов,
 * делегируя вызов к [TransactionRepository].
 * @property expensesRepository Источник данных о транзакциях [TransactionRepository]
 * @constructor Создает UseCase для работы с расходами
 * @param expensesRepository Репозиторий транзакций (внедряется через DI)
 */
class GetExpensesUseCaseImpl @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : GetExpensesUseCase {

    override suspend operator fun invoke(
        startDate: String?,
        endDate: String?
    ): List<Expense> {
        return expensesRepository.getExpensesByPeriod(
            startDate = startDate,
            endDate = endDate
        )
    }
}
