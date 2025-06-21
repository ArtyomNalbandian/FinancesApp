package com.example.financesapp.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetAccountsUseCase
import com.example.financesapp.presentation.income.IncomeEvent
import com.example.financesapp.presentation.income.IncomeState
import com.example.financesapp.utils.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModelFactory(
    private val usecase: GetAccountsUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(usecase, networkMonitor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AccountViewModel(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _state = MutableStateFlow<AccountState>(AccountState.Loading)
    val state: StateFlow<AccountState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<AccountEvent>()
    val event: SharedFlow<AccountEvent> = _event.asSharedFlow()

    private var isFirstLoad = true

    init {
        observeNetwork()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { connected ->
                if (connected && (isFirstLoad || _state.value is AccountState.Error)) {
                    loadAccount()
                } else if (!connected) {
                    _state.value = AccountState.Error("Нет подключения к интернету")
                    _event.emit(AccountEvent.ShowError("Нет подключения к интернету"))
                }
            }
        }
    }

    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            is AccountIntent.ChangeCurrency -> changeCurrency(intent.currency)
            is AccountIntent.LoadAccount -> loadAccount()
            is AccountIntent.HideCurrencySelector -> updateCurrencySelector(false)
            is AccountIntent.ShowCurrencySelector -> updateCurrencySelector(true)
        }
    }

    fun retryLoad() {
        loadAccount()
    }

    private fun loadAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = AccountState.Loading
            try {
                val account = getAccountsUseCase.invoke()
                _state.value = AccountState.Content(account, isCurrencySelectorVisible = false)
            } catch (e: Exception) {
                _state.value = AccountState.Error(e.message.orEmpty())
                _event.emit(AccountEvent.ShowError("Ошибка загрузки"))
            }
        }
    }

    private fun updateCurrencySelector(visible: Boolean) {
        val current = _state.value
        if (current is AccountState.Content) {
            _state.value = current.copy(isCurrencySelectorVisible = visible)
        }
    }

    private fun changeCurrency(newCurrency: String) {
        viewModelScope.launch {
            val current = _state.value
            if (current is AccountState.Content) {
                try {
                    val newAccount = current.account.copy(currency = newCurrency)
                    _state.value = current.copy(
                        account = newAccount,
                        isCurrencySelectorVisible = false
                    )
                    _event.emit(AccountEvent.ShowError("Валюта изменена на $newCurrency"))
                } catch (e: Exception) {
                    _event.emit(AccountEvent.ShowError("Не удалось изменить валюту"))
                }
            }
        }
    }
}
