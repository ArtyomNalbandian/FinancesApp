package com.example.financesapp.presentation.screens.edit_account

sealed interface EditAccountEvent {

    data class AccountUpdated(val message: String) : EditAccountEvent
    data class ShowError(val message: String) : EditAccountEvent
    data object NavigateBack : EditAccountEvent
}
