package com.example.financesapp.presentation.account


sealed interface AccountIntent {
    data class OpenCurrencySelector(val accountId: Int) : AccountIntent
    data object CloseCurrencySelector : AccountIntent
    data class ChangeCurrency(val accountId: Int, val currency: String) : AccountIntent
    data class EditAccount(val accountId: Int) : AccountIntent
    data object CreateAccount : AccountIntent
}