package com.example.financesapp.presentation.screens.account


sealed interface AccountEvent {
    data class ShowError(val message: String) : AccountEvent
}