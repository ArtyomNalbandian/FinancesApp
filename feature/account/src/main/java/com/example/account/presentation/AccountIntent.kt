package com.example.account.presentation


sealed interface AccountIntent {

    data object LoadAccount : AccountIntent
    data class ShowCurrencySelector(val accountId: Int) : AccountIntent
    data object HideCurrencySelector : AccountIntent
    data class ChangeCurrency(val accountId: Int, val currency: String) : AccountIntent
}
