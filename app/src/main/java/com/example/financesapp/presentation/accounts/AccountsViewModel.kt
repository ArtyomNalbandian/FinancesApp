package com.example.financesapp.presentation.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.account.Account
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountsViewModel : ViewModel() {

    private val _accountsState = MutableStateFlow<AccountsState>(AccountsState.Loading)
    val accountsState: StateFlow<AccountsState> = _accountsState.asStateFlow()

    private val _accountsEvents = MutableSharedFlow<AccountsEvent>()
    val accountsEvent: SharedFlow<AccountsEvent> = _accountsEvents.asSharedFlow()

    private val _currencySelectorState =
        MutableStateFlow<CurrencySelectorState>(CurrencySelectorState.Hidden)
    val currencySelectorState: StateFlow<CurrencySelectorState> =
        _currencySelectorState.asStateFlow()

    private val accountsList = listOf(
        Account(1, "Сбербанк", "300 000", "₽"),
        Account(2, "Тиньк", "200 000", "$"),
        Account(3, "Альфа", "164 000", "€")
    )

    init {
        handleIntent(AccountsIntent.LoadAccounts)
    }

    fun handleIntent(intent: AccountsIntent) {
        when (intent) {
            is AccountsIntent.EditAccount -> editAccount(intent.accountId)
            is AccountsIntent.CloseCurrencySelector -> closeCurrencySelector()
            is AccountsIntent.LoadAccounts -> loadAccounts()
            is AccountsIntent.OpenCurrencySelector -> openCurrencySelector(intent.accountId)
            is AccountsIntent.ChangeCurrency -> changeCurrency(intent.accountId, intent.currency)
        }
    }

    private fun changeCurrency(accountId: Int, newCurrency: String) {
        val currentState = _accountsState.value
        if (currentState is AccountsState.Success) {
            val updatedAccounts = currentState.accounts.map { account ->
                if (account.id == accountId) {
                    account.copy(currency = newCurrency)
                } else account
            }
            _accountsState.value = currentState.copy(accounts = updatedAccounts)
            closeCurrencySelector()
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accountsState.value = AccountsState.Loading
            delay(1000) // имитация загрузки
            _accountsState.value = AccountsState.Success(accountsList)
        }
    }

    private fun editAccount(accountId: Int) {
        viewModelScope.launch {
            _accountsEvents.emit(AccountsEvent.NavigateToEditAccountScreen(accountId.toString()))
        }
    }

    private fun openCurrencySelector(accountId: Int) {
        _currencySelectorState.value = CurrencySelectorState.Visible(accountId)
    }

    private fun closeCurrencySelector() {
        _currencySelectorState.value = CurrencySelectorState.Hidden
    }
}
