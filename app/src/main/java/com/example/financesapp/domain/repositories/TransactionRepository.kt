package com.example.financesapp.domain.repositories

import com.example.financesapp.domain.models.expenses.Expense
import com.example.financesapp.domain.models.income.Income

/**
 * Репозиторий для работы с транзакциями.
 */
interface TransactionRepository {

    /**
     * Возвращает расходы] за указанный период.
     * @param startDate Начальная дата периода
     * @param endDate Конечная дата периода
     * @return Список расходов [Expense]
     */
    suspend fun getExpensesByPeriod(startDate: String?, endDate: String?): List<Expense>

    /**
     * Возвращает доходы за указанный период.
     * @param startDate Начальная дата периода
     * @param endDate Конечная дата периода
     * @return Список доходов [Income]
     */
    suspend fun getIncomesByPeriod(startDate: String?, endDate: String?): List<Income>

}
