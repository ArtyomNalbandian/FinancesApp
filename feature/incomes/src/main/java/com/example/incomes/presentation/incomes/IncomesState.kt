package com.example.incomes.presentation.incomes

import com.example.common.model.income.Income

internal sealed interface IncomesState {

    data object Loading : IncomesState
    data class Content(
        val income: List<Income>,
        val total: String,
        val currency: String
    ) : IncomesState

    data class Error(val message: String) : IncomesState
}
