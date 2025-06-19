package com.example.financesapp.domain.usecase

import com.example.financesapp.domain.income.Income


interface GetIncomesUseCase {

    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): List<Income>

}