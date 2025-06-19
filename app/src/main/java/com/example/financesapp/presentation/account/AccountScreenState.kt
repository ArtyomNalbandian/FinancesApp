package com.example.financesapp.presentation.account

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
    data class Success(val accounts: List<com.example.financesapp.domain.account.Account>) : AccountState()
    data class Error(val message: String) : AccountState()
}

data class Account(
    val id: Long,
    val name: String,
    val balance: String,
    val currency: String // "RUB", "USD", "EUR"
)

data class CurrencyItem(
    val code: String, // "RUB", "USD", "EUR"
    val name: String,
    val symbol: String,
    val iconRes: Int
)

sealed class CurrencySelectorState {
    data object Hidden : CurrencySelectorState()
    data class Visible(
        val accountId: Long, // Для какого счёта выбираем валюту
        val currentCurrency: String
    ) : CurrencySelectorState()
}