package com.example.financesapp.presentation.screens.expenses

sealed interface ExpensesEvent {

    data class ShowError(val message: String) : ExpensesEvent

}