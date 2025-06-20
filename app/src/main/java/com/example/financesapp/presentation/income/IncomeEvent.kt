package com.example.financesapp.presentation.income

sealed interface IncomeEvent {
    data class ShowError(val message: String) : IncomeEvent
}