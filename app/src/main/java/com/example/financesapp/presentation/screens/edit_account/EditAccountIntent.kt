package com.example.financesapp.presentation.screens.edit_account

sealed interface EditAccountIntent {

    data class ShowCurrencySelector(val accountId: Int) : EditAccountIntent
    data object HideCurrencySelector : EditAccountIntent
    data class ChangeCurrency(val accountId: Int, val currency: String) : EditAccountIntent
}
