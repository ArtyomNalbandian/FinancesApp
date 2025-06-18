package com.example.financesapp.presentation.account

//import com.example.financesapp.data.remote.models.account.Account

//data class AccountsState(
//    val accounts: List<Account> = emptyList(),
//    val isLoading: Boolean = false,
//    val error: String? = null
//)
//
//sealed class AccountsEvent {
//    data object LoadAccounts : AccountsEvent()
//    data class AccountsLoaded(val accounts: List<Account>) : AccountsEvent()
//    data class Error(val message: String) : AccountsEvent()
//}

//sealed class AccountState {
//    data object Loading : AccountState()
//    data class Success(
//        val accounts: List<Account>,
//        val selectedCurrency: CurrencyItem,
//        val isCurrencySelectorVisible: Boolean = false
//    ) : AccountState()
//
//    data class Error(val message: String) : AccountState()
//}

// Состояния, события и намерения
sealed interface AccountState {
    data object Loading : AccountState
    data class Content(
        val accounts: List<Account>,
        val selectedCurrency: Map<Long, String> // accountId to currency
    ) : AccountState
    data class Error(val message: String) : AccountState
}

// Модели данных
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