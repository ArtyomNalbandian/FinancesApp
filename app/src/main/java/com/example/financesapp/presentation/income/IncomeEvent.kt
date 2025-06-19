package com.example.financesapp.presentation.income

sealed interface IncomeEvent {
    data object NavigateToCreateIncomeScreen : IncomeEvent
    data object NavigateToIncomeHistoryScreen : IncomeEvent
    data class NavigateToEditIncomeScreen(val incomeId: String) : IncomeEvent
    data class ShowError(val message: String) : IncomeEvent
}