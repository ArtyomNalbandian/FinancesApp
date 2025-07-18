package com.example.edit_account.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.account.domain.usecase.interfaces.UpdateAccountUseCase
import com.example.common.model.account.Account
import com.example.network.dto.account.AccountRequestDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EditAccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EditAccountState>(EditAccountState.Loading)
    val state: StateFlow<EditAccountState> = _state.asStateFlow()

    private val _editAccountState = MutableStateFlow<Account?>(null)
    val editAccountState: StateFlow<Account?> = _editAccountState.asStateFlow()

    private val _event = MutableSharedFlow<EditAccountEvent>()
    val event: SharedFlow<EditAccountEvent> = _event

    init {
        viewModelScope.launch {
            try {
                val account = getAccountUseCase.invoke()
                Log.d("testLog", "state changed to Content --- $account")
                _state.value = EditAccountState.Content(account)
                _editAccountState.value = account
            } catch (e: Exception) {
                _state.value = EditAccountState.Error("Не удалось загрузить счет: ${e.message}")
            }
        }
    }

    fun submit(newName: String, newBalance: String, newCurrency: String) {
        val current = _editAccountState.value ?: return
        viewModelScope.launch {
            try {
                val request = AccountRequestDto(
                    name = newName,
                    balance = newBalance,
                    currency = newCurrency
                )
                val updatedAccount = updateAccountUseCase.invoke(current.id, request)
                _editAccountState.value = updatedAccount
                _event.emit(EditAccountEvent.ShowSuccess("Счет обновлен"))
                Log.d("testLog", "submit in ViewModel --- ${_state.value}")
            } catch (e: Exception) {
                _event.emit(EditAccountEvent.ShowError("Ошибка при сохранении: ${e.message}"))
            }
        }
    }

    fun handleIntent(intent: EditAccountIntent) {
        when (intent) {
            is EditAccountIntent.ChangeCurrency -> changeCurrency(intent.currency)
            is EditAccountIntent.HideCurrencySelector -> updateCurrencySelector(false)
            is EditAccountIntent.ShowCurrencySelector -> updateCurrencySelector(true)
        }
    }

    private fun updateCurrencySelector(visible: Boolean) {
        val current = _state.value
        if (current is EditAccountState.Content) {
            _state.value = current.copy(isCurrencySelectorVisible = visible)
        }
    }

    private fun changeCurrency(newCurrency: String) {
        viewModelScope.launch {
            val current = _state.value
            if (current is EditAccountState.Content) {
                try {
                    val newAccount = current.account.copy(currency = newCurrency)
                    _state.value = current.copy(
                        account = newAccount,
                        isCurrencySelectorVisible = false
                    )
                } catch (e: Exception) {
                    _event.emit(EditAccountEvent.ShowError("Не удалось изменить валюту"))
                }
            }
        }
    }
}
