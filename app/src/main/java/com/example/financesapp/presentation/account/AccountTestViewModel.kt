//package com.example.financesapp.presentation.account
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharedFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.receiveAsFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//
//class AccountsViewModel : ViewModel() {
//
//    private val _state = MutableStateFlow<AccountState>(AccountState.Loading)
//    val state: StateFlow<AccountState> = _state.asStateFlow()
//
//    private val _events = Channel<AccountEvent>()
//    val events = _events.receiveAsFlow()
//
//    fun handleIntent(intent: AccountIntent) {
//        when (intent) {
//            AccountIntent.LoadAccounts -> loadAccounts()
//            is AccountIntent.ChangeAccountCurrency -> changeCurrency(intent.accountId, intent.currency)
//            is AccountIntent.OpenCurrencySelector -> _events.trySend(AccountEvent.ShowCurrencySelector(intent.accountId))
//        }
//    }
//
//    private fun loadAccounts() {
//        viewModelScope.launch {
//            _state.value = AccountState.Loading
//            try {
//                delay(3000)
//                // Здесь должен быть вызов репозитория
//                val accounts = listOf(
//                    Account(1, "Основной", "1000.00", "RUB"),
//                    Account(2, "Долларовый", "500.00", "USD"),
//                    Account(3, "Евро", "300.00", "EUR")
//                )
//
//                // Инициализируем выбранные валюты (по умолчанию - валюта счета)
//                val selectedCurrency = accounts.associate { it.id to it.currency }
//
//                _state.value = AccountState.Content(accounts, selectedCurrency)
//            } catch (e: Exception) {
//                _state.value = AccountState.Error("Ошибка загрузки счетов")
//                _events.trySend(AccountEvent.ShowError(e.message ?: "Неизвестная ошибка"))
//            }
//        }
//    }
//
//    private fun changeCurrency(accountId: Long, currency: String) {
//        val currentState = _state.value
//        if (currentState is AccountState.Content) {
//            _state.value = currentState.copy(
//                selectedCurrency = currentState.selectedCurrency + (accountId to currency)
//            )
//        }
//    }
//
//    /*private val _accountState = MutableStateFlow<AccountState>(AccountState.Loading)
//    val accountState: StateFlow<AccountState> = _accountState.asStateFlow()
//
//    private val _accountEvents = Channel<AccountEvent>()
//    val accountEvents = _accountEvents.receiveAsFlow()
//
//    fun handleIntent(intent: AccountIntent) {
//        when (intent) {
//            is AccountIntent.LoadAccounts -> loadAccounts()
//            is AccountIntent.SelectCurrency -> selectCurrency(intent.currency)
//            is AccountIntent.CloseCurrencySelector -> closeCurrencySelector()
//            is AccountIntent.OpenCurrencySelector -> openCurrencySelector()
//            is AccountIntent.BalanceClick -> handleBalanceClick(intent.accountId)
//        }
//    }
//
//    private fun loadAccounts() {
//        viewModelScope.launch {
//            _accountState.value = AccountState.Loading
//            try {
//                val accounts = accountRepository.getAccounts()
//                _accountState.value = AccountState.Success(
//                    accounts = accounts,
//                    selectedCurrency = CurrencyItem(name, 2)
//                )
//            } catch (e: Exception) {
//                _accountState.value = AccountState.Error(
//                    message = "Не удалось загрузить счета"
//                )
//                _accountEvents.send(AccountEvent.ShowError("Ошибка загрузки: ${e.localizedMessage}"))
//            }
//        }
//    }
//
//    private fun selectCurrency(currency: CurrencyItem) {
//        val currentState = _accountState.value
//        if (currentState is AccountState.Success) {
//            _accountState.value = currentState.copy(
//                selectedCurrency = currency,
//                isCurrencySelectorVisible = false
//            )
//            loadAccounts()
//        }
//    }
//
//    private fun closeCurrencySelector() {
//        _accountState.update { currentState ->
//            when(currentState) {
//                is AccountState.Success -> currentState.copy(
//                    isCurrencySelectorVisible = false
//                )
//                else -> currentState
//            }
//        }
//    }
//
//    private fun openCurrencySelector() {
//        _accountState.update { currentState ->
//            when(currentState) {
//                is AccountState.Success -> currentState.copy(
//                    isCurrencySelectorVisible = true
//                )
//                else -> currentState
//            }
//        }
//    }
//
//    private fun handleBalanceClick(accountId: String) {
//        viewModelScope.launch {
//            try {
//                _accountEvents.send(AccountEvent.NavigateToEditAccountScreen(accountId))
//            } catch (e: Exception) {
//                _accountEvents.send(AccountEvent.ShowError("Не удалось открыть детали счета"))
//            }
//        }
//    }*/
//
////    private fun selectCurrency(currency: CurrencyItem) {
////        _accountState.update {
////            it.copy(
////
////            )
////        }
////        loadAccounts() // Перезагружаем счета для новой валюты
////    }
//
////    init {
////        loadAccounts()
////    }
//
//    /*    private fun loadAccounts() {
//            viewModelScope.launch {
//                _accountState.update { it.copy(isLoading = true, error = null) }
//                try {
//                    val accounts = repository.getAccounts()
//                    _accountState.update {
//                        it.copy(
//                            accounts = accounts,
//                            isLoading = false
//                        )
//                    }
//                } catch (e: Exception) {
//                    _accountState.update {
//                        it.copy(
//                            isLoading = false,
//                            error = e.message ?: "Unknown error occurred"
//                        )
//                    }
//                }
//            }
//        }*/
//
//    class Factory : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return AccountsViewModel() as T
//        }
//    }
//
//
//}