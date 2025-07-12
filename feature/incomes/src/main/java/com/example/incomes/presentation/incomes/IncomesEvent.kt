package com.example.incomes.presentation.incomes

internal sealed interface IncomesEvent {

    data class ShowError(val message: String) : IncomesEvent
}
