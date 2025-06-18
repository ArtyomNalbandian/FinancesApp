package com.example.financesapp.presentation.accounts

sealed interface AccountsIntent {
    data object LoadAccounts : AccountsIntent
    data class OpenCurrencySelector(val accountId: Int) : AccountsIntent
    data object CloseCurrencySelector : AccountsIntent
    data class ChangeCurrency(val accountId: Int, val currency: String) : AccountsIntent
    data class EditAccount(val accountId: Int) : AccountsIntent
}
