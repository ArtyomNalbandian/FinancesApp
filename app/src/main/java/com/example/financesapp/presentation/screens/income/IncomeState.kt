package com.example.financesapp.presentation.screens.income

import com.example.financesapp.domain.models.income.Income

sealed interface IncomeState {

    data object Loading : IncomeState
    data class Content(
        val income: List<Income>,
        val total: String,
        val currency: String
    ) : IncomeState

    data class Error(val message: String) : IncomeState
}
