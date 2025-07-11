package com.example.account.presentation


sealed interface AccountEvent {

    data class ShowError(val message: String) : AccountEvent
}
