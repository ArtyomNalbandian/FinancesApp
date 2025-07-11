//package com.example.financesapp.presentation.screens.account
//
//import com.example.common.model.account.Account
//
//
//sealed interface AccountState {
//
//    data object Loading : AccountState
//    data class Content(
//        val account: Account,
//        val isCurrencySelectorVisible: Boolean
//    ) : AccountState
//
//    data class Error(val message: String) : AccountState
//}
