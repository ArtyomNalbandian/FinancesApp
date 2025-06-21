package com.example.financesapp.presentation.income

import com.example.financesapp.domain.income.Income

sealed interface IncomeState {
    data object Loading : IncomeState
    data class Content(val income: List<Income>, val total: String, val currency: String) :
        IncomeState
    data class Error(val message: String) : IncomeState
}
