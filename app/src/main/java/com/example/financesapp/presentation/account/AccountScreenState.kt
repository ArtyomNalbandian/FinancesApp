package com.example.financesapp.presentation.account

import com.example.financesapp.domain.account.Account

//sealed interface AccountState {
//    data object Loading : AccountState
//    data class Content(
//        val accounts: List<Account>,
//        val selectedCurrency: String
//    ) : AccountState
//    data class Error(val message: String) : AccountState
//}

sealed class AccountState {
    data object Loading : AccountState()
    data class Success(val account: Account) : AccountState()
    data class Error(val message: String) : AccountState()
}
