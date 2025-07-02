package com.example.financesapp.presentation.screens.edit_account

import com.example.financesapp.domain.models.account.Account

sealed interface EditAccountIntent {

    data class Submit(val account: Account) : EditAccountIntent
    data object Cancel : EditAccountIntent
}
