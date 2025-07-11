package com.example.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.common.model.account.Account
import kotlinx.coroutines.Dispatchers
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
    private val getAccountsUseCase: GetAccountUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AccountState>(AccountState.Loading)
    val state: StateFlow<AccountState> = _state.asStateFlow()

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    private val _event = MutableSharedFlow<AccountEvent>()
    val event: SharedFlow<AccountEvent> = _event.asSharedFlow()

    fun loadAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = AccountState.Loading
            try {
                val account = getAccountsUseCase.invoke()
                _selectedAccount.value = account
                _state.value = AccountState.Content(account, isCurrencySelectorVisible = false)
            } catch (e: Exception) {
                _state.value = AccountState.Error(e.message.orEmpty())
                _event.emit(AccountEvent.ShowError("Ошибка загрузки"))
            }
        }
    }
}
