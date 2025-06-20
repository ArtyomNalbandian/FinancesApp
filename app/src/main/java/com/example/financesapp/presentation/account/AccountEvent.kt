package com.example.financesapp.presentation.account


sealed interface AccountEvent {
    data class ShowError(val message: String) : AccountEvent
}