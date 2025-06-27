package com.example.financesapp.presentation.screens.income

sealed interface IncomeEvent {

    data class ShowError(val message: String) : IncomeEvent

}
