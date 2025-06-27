package com.example.financesapp.presentation.screens.account


sealed interface AccountIntent {
    data object LoadAccount: AccountIntent
    data class ShowCurrencySelector(val accountId: Int) : AccountIntent
    data object HideCurrencySelector : AccountIntent
    data class ChangeCurrency(val accountId: Int, val currency: String) : AccountIntent
}