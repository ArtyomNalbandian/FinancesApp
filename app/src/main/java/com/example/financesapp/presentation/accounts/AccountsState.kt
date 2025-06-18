package com.example.financesapp.presentation.accounts

import com.example.financesapp.domain.account.Account

sealed class AccountsState {
    data object Loading : AccountsState()
    data class Success(val accounts: List<Account>) : AccountsState()
    data class Error(val message: String) : AccountsState()
}

sealed class CurrencySelectorState {
    data object Hidden : CurrencySelectorState()
    data class Visible(val selectedAccountId: Int) : CurrencySelectorState()
}