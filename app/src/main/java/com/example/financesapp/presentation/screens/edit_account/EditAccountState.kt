package com.example.financesapp.presentation.screens.edit_account

import com.example.common.model.account.Account


sealed interface EditAccountState {

    data object Loading : EditAccountState
    data class Content(
        val account: Account,
        val isCurrencySelectorVisible: Boolean = false
    ) : EditAccountState

    data class Error(val message: String) : EditAccountState
}
