package com.example.financesapp.presentation.screens.account

import com.example.financesapp.domain.models.account.Account


sealed interface AccountState {
    data object Loading : AccountState
    data class Content(
        val account: Account,
        val isCurrencySelectorVisible: Boolean
    ) : AccountState
    data class Error(val message: String) : AccountState
}

//sealed class CurrencySelectorState {
//    data object Hidden : CurrencySelectorState()
//    data class Visible(val selectedAccountId: Int) : CurrencySelectorState()
//}