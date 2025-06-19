package com.example.financesapp.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.account.Account
import com.example.financesapp.domain.usecase.GetAccountsUseCase
import com.example.financesapp.presentation.accounts.CurrencySelectorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AccountViewModelFactory(
    private val usecase: GetAccountsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(usecase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AccountViewModel(
    private val getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {

    private val _accountState = MutableStateFlow<AccountState>(AccountState.Loading)
    val accountsState: StateFlow<AccountState> = _accountState.asStateFlow()

    private val _accountEvents = Channel<AccountEvent>()
    val accountsEvent = _accountEvents.receiveAsFlow()

    private val _currencySelectorState =
        MutableStateFlow<CurrencySelectorState>(CurrencySelectorState.Hidden)
    val currencySelectorState: StateFlow<CurrencySelectorState> =
        _currencySelectorState.asStateFlow()

    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            is AccountIntent.EditAccount -> editAccount(intent.accountId)
            is AccountIntent.CreateAccount -> createAccount()
            is AccountIntent.CloseCurrencySelector -> closeCurrencySelector()
            is AccountIntent.OpenCurrencySelector -> openCurrencySelector(intent.accountId)
            is AccountIntent.ChangeCurrency -> changeCurrency(intent.accountId, intent.currency)
        }
    }

    init {
        loadAccounts()
    }

    private fun changeCurrency(accountId: Int, newCurrency: String) {
        val currentState = _accountState.value
        if (currentState is AccountState.Success) {
            val updatedAccounts = currentState.accounts.map { account ->
                if (account.id == accountId) {
                    account.copy(currency = newCurrency)
                } else account
            }
            _accountState.value = currentState.copy(accounts = updatedAccounts)
            closeCurrencySelector()
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch(Dispatchers.IO) {
            _accountState.value = AccountState.Loading
            try {
                val accounts = getAccountsUseCase.invoke()
                _accountState.value = AccountState.Success(accounts)
            } catch (e: Exception) {
                _accountState.value = AccountState.Error(
                    message = e.message ?: "Не удалось загрузить аккаунт"
                )
            }
        }
    }

    private fun editAccount(accountId: Int) {
        viewModelScope.launch {
            _accountEvents.send(AccountEvent.NavigateToEditAccountScreen(accountId.toString()))
        }
    }

    private fun createAccount() {
        viewModelScope.launch {
            _accountEvents.send(AccountEvent.NavigateToCreateAccountScreen)
        }
    }

    private fun openCurrencySelector(accountId: Int) {
        _currencySelectorState.value = CurrencySelectorState.Visible(accountId)
    }

    private fun closeCurrencySelector() {
        _currencySelectorState.value = CurrencySelectorState.Hidden
    }

}