package com.example.financesapp.presentation.screens.edit_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.models.account.Account
import com.example.financesapp.domain.usecases.interfaces.GetAccountByIdUseCase
import com.example.financesapp.domain.usecases.interfaces.UpdateAccountUseCase
import com.example.financesapp.presentation.screens.account.AccountEvent
import com.example.financesapp.presentation.screens.account.AccountIntent
import com.example.financesapp.presentation.screens.account.AccountState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditAccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EditAccountState>(EditAccountState.Loading)
    val state: StateFlow<EditAccountState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<EditAccountEvent>()
    val event: SharedFlow<EditAccountEvent> = _event.asSharedFlow()

//    private var currentAccount: Account? = null

//    init {
//        loadAccount(currentAccount?.id.toString())
//    }

    fun handleIntent(intent: EditAccountIntent) {
        when (intent) {
            is EditAccountIntent.ChangeCurrency -> changeCurrency(intent.currency)
            is EditAccountIntent.HideCurrencySelector -> updateCurrencySelector(false)
            is EditAccountIntent.ShowCurrencySelector -> updateCurrencySelector(true)
            is EditAccountIntent.Submit -> { /*TODO()*/ }
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
                    _event.emit(EditAccountEvent.ShowError("Валюта изменена на $newCurrency"))
                } catch (e: Exception) {
                    _event.emit(EditAccountEvent.ShowError("Не удалось изменить валюту"))
                }
            }
        }
    }

//    fun loadAccount(accountId: String) {
//        viewModelScope.launch {
//            _state.value = EditAccountState.Loading
//            try {
//                val account = getAccountByIdUseCase(accountId)
//                currentAccount = account
//                _state.value = EditAccountState.Content(account)
//            } catch (e: Exception) {
//                _state.value = EditAccountState.Error("Не удалось загрузить счёт")
//                _event.emit(EditAccountEvent.ShowError("Ошибка при загрузке счёта"))
//            }
//        }
//    }

//    fun handleIntent(intent: EditAccountIntent) {
//        when (intent) {
//            is EditAccountIntent.Submit -> {
//                updateAccount(intent.account)
//            }
//            is EditAccountIntent.Cancel -> {
//                _event.tryEmit(EditAccountEvent.NavigateBack)
//            }
//        }
//    }

//    private fun updateAccount(account: Account) {
//        viewModelScope.launch {
//            _state.value = EditAccountState.Loading
//            try {
//                val updated = updateAccountUseCase(account)
//                _state.value = EditAccountState.Content(updated)
//                _event.emit(EditAccountEvent.AccountUpdated("Счёт обновлён"))
//            } catch (e: Exception) {
//                _state.value = EditAccountState.Error(e.message.orEmpty())
//                _event.emit(EditAccountEvent.ShowError("Не удалось обновить счёт"))
//            }
//        }
//    }
}
