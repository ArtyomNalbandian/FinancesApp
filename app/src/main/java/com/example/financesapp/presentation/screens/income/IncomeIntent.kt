package com.example.financesapp.presentation.screens.income

sealed interface IncomeIntent {
    data class LoadIncome(val startDate: String, val endDate: String) : IncomeIntent
}