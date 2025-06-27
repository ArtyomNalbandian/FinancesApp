package com.example.financesapp.domain.usecases.interfaces

import com.example.financesapp.domain.models.income.Income


/**
 * UseCase для получения списка доходов за указанный период.
 * @param startDate начальная дата периода в формате ISO8601 (yyyy-MM-dd)
 * @param endDate конечная дата периода в формате ISO8601 (yyyy-MM-dd)
 * @return Список [Income] - доменных моделей доходов
 */
interface GetIncomesUseCase {

    suspend operator fun invoke(
        startDate: String? = null,
        endDate: String? = null
    ): List<Income>

}
