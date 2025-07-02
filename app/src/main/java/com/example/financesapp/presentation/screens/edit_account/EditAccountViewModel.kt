package com.example.financesapp.presentation.screens.edit_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.models.account.Account
import com.example.financesapp.domain.usecases.interfaces.GetAccountByIdUseCase
import com.example.financesapp.domain.usecases.interfaces.UpdateAccountUseCase
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

    private val _state = MutableStateFlow<EditAccountState>(EditAccountState.Idle)
    val state: StateFlow<EditAccountState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<EditAccountEvent>()
    val event: SharedFlow<EditAccountEvent> = _event.asSharedFlow()

    private var currentAccount: Account? = null

    fun loadAccount(accountId: String) {
        viewModelScope.launch {
            _state.value = EditAccountState.Loading
            try {
                val account = getAccountByIdUseCase(accountId)
                currentAccount = account
                _state.value = EditAccountState.Content(account)
            } catch (e: Exception) {
                _state.value = EditAccountState.Error("Не удалось загрузить счёт")
                _event.emit(EditAccountEvent.ShowError("Ошибка при загрузке счёта"))
            }
        }
    }

    fun handleIntent(intent: EditAccountIntent) {
        when (intent) {
            is EditAccountIntent.Submit -> {
                updateAccount(intent.account)
            }
            is EditAccountIntent.Cancel -> {
                _event.tryEmit(EditAccountEvent.NavigateBack)
            }
        }
    }

    private fun updateAccount(account: Account) {
        viewModelScope.launch {
            _state.value = EditAccountState.Loading
            try {
                val updated = updateAccountUseCase(account)
                _state.value = EditAccountState.Content(updated)
                _event.emit(EditAccountEvent.AccountUpdated("Счёт обновлён"))
            } catch (e: Exception) {
                _state.value = EditAccountState.Error(e.message.orEmpty())
                _event.emit(EditAccountEvent.ShowError("Не удалось обновить счёт"))
            }
        }
    }
}
