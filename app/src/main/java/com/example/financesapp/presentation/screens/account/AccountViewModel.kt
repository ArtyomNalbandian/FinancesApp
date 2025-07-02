package com.example.financesapp.presentation.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecases.interfaces.GetAccountsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для управления состоянием счета и валюты.
 * @property getAccountsUseCase UseCase для получения данных аккаунта
 */
class AccountViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AccountState>(AccountState.Loading)
    val state: StateFlow<AccountState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<AccountEvent>()
    val event: SharedFlow<AccountEvent> = _event.asSharedFlow()

    init {
        loadAccount()
    }

    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            is AccountIntent.ChangeCurrency -> changeCurrency(intent.currency)
            is AccountIntent.LoadAccount -> loadAccount()
            is AccountIntent.HideCurrencySelector -> updateCurrencySelector(false)
            is AccountIntent.ShowCurrencySelector -> updateCurrencySelector(true)
        }
    }

    private fun loadAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
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
