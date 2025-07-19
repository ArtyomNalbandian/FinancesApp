package com.example.expenses.presentation.expenses_analysis

internal sealed interface ExpensesAnalysisIntent {

    data class LoadAnalysis(
        val startDate: String,
        val endDate: String
    ) : ExpensesAnalysisIntent
}
