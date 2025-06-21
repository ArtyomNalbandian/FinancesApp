package com.example.financesapp.domain.usecase

import com.example.financesapp.domain.income.Income


interface GetIncomesUseCase {

    suspend operator fun invoke(
        startDate: String? = null,
        endDate: String? = null
    ): List<Income>

}