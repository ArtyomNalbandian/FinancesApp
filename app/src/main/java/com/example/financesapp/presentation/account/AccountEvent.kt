package com.example.financesapp.presentation.account


sealed interface AccountEvent {
    data object NavigateToCreateAccountScreen : AccountEvent
    data class NavigateToEditAccountScreen(val accountId: String) : AccountEvent
    data class ShowError(val message: String) : AccountEvent
}