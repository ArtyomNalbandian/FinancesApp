package com.example.incomes.presentation.incomes_analysis

internal sealed interface IncomesAnalysisIntent {

    data class LoadAnalysis(
        val startDate: String,
        val endDate: String
    ) : IncomesAnalysisIntent
}
