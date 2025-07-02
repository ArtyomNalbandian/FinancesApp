package com.example.financesapp.presentation.screens.edit_account

import com.example.financesapp.domain.models.account.Account

sealed interface EditAccountState {

    data object Idle : EditAccountState
    data object Loading : EditAccountState
    data class Content(val account: Account) : EditAccountState
    data class Error(val message: String) : EditAccountState
}
