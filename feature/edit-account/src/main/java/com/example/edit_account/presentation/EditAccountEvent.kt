package com.example.edit_account.presentation

internal sealed interface EditAccountEvent {

    data class ShowError(val message: String) : EditAccountEvent
    data class ShowSuccess(val message: String) : EditAccountEvent
}
