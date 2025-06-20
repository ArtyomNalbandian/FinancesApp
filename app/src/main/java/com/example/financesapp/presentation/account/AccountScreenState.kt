package com.example.financesapp.presentation.account

import com.example.financesapp.domain.account.Account


sealed class AccountState {
    data object Loading : AccountState()
    data class Success(val account: Account) : AccountState()
    data class Error(val message: String) : AccountState()
}

sealed class CurrencySelectorState {
    data object Hidden : CurrencySelectorState()
    data class Visible(val selectedAccountId: Int) : CurrencySelectorState()
}