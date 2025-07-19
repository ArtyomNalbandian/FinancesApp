package com.example.incomes.presentation.incomes_analysis

import com.example.common.model.income.Income

internal sealed class IncomesAnalysisState {

    data object Loading : IncomesAnalysisState()
    data class Content(
        val items: List<Income>,
        val total: String
    ) : IncomesAnalysisState()

    data class Error(val message: String) : IncomesAnalysisState()
}
