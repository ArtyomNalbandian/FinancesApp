package com.example.financesapp.presentation.screens.edit_account

import com.example.financesapp.domain.models.account.Account

sealed interface EditAccountIntent {

    data class Submit(val account: Account) : EditAccountIntent

    data class ShowCurrencySelector(val accountId: Int) : EditAccountIntent
    data object HideCurrencySelector : EditAccountIntent
    data class ChangeCurrency(val accountId: Int, val currency: String) : EditAccountIntent
}
