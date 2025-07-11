package com.example.edit_account.presentation

import com.example.common.model.account.Account


internal sealed interface EditAccountState {

    data object Loading : EditAccountState
    data class Content(
        val account: Account,
        val isCurrencySelectorVisible: Boolean = false
    ) : EditAccountState

    data class Error(val message: String) : EditAccountState
}
