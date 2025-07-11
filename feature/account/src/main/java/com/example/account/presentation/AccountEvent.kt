package com.example.account.presentation


interface AccountEvent {

    data class ShowError(val message: String) : AccountEvent
}
