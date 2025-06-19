package com.example.financesapp.presentation.income

sealed interface IncomeIntent {
    data class LoadIncome(val accountId: Int, val startDate: String, val endDate: String) : IncomeIntent
    data class OpenHistoryOfIncome(val accountId: Int) : IncomeIntent
    data object CreateIncome : IncomeIntent
    data class EditIncome(val accountId: Int, val incomeId: Int) : IncomeIntent
}