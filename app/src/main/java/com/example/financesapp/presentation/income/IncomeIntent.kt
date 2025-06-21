package com.example.financesapp.presentation.income

sealed interface IncomeIntent {
    data class LoadIncome(val startDate: String, val endDate: String) : IncomeIntent
}