package com.example.financesapp.domain.usecases.interfaces

import com.example.common.model.income.Income

interface GetIncomesUseCase {

    suspend operator fun invoke(
        startDate: String? = null,
        endDate: String? = null
    ): List<Income>
}
