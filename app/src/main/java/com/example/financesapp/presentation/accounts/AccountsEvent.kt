package com.example.financesapp.presentation.accounts

sealed interface AccountsEvent {
    data object NavigateToCreateAccountScreen : AccountsEvent
    data class NavigateToEditAccountScreen(val accountId: String) : AccountsEvent
    data class ShowError(val message: String) : AccountsEvent
}