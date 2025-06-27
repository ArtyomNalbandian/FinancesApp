package com.example.financesapp.domain.usecases.interfaces

import com.example.financesapp.domain.models.expenses.Expense


/**
 * UseCase для получения списка расходов за указанный период.
 * @param startDate начальная дата периода в формате ISO8601 (yyyy-MM-dd)
 * @param endDate конечная дата периода в формате ISO8601 (yyyy-MM-dd)
 * @return Список [Expense] - доменных моделей расходов
 */
interface GetExpensesUseCase {

    suspend operator fun invoke(
        startDate: String? = null,
        endDate: String? = null
    ): List<Expense>

}
