package com.example.financesapp.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetExpensesUseCase
import com.example.financesapp.presentation.account.AccountState
import com.example.financesapp.presentation.income.IncomeEvent
import com.example.financesapp.presentation.income.IncomeState
import com.example.financesapp.utils.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExpensesViewModelFactory(
    private val repository: GetExpensesUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesViewModel::class.java)) {
            return ExpensesViewModel(repository, networkMonitor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class ExpensesViewModel(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesState>(ExpensesState.Loading)
    val state: StateFlow<ExpensesState> = _state

    private val _event = MutableSharedFlow<ExpensesEvent>()
    val event: SharedFlow<ExpensesEvent> = _event.asSharedFlow()

    private var isFirstLoad = true

    init {
        observeNetwork()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { connected ->
                if (connected && (isFirstLoad || _state.value is ExpensesState.Error)) {
                    loadExpenses()
                } else if (!connected) {
                    _state.value = ExpensesState.Error("Нет подключения к интернету")
                    _event.emit(ExpensesEvent.ShowError("Нет подключения к интернету"))
                }
            }
        }
    }

    fun retryLoad() {
        loadExpenses()
    }

    private fun loadExpenses() {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ExpensesState.Loading
            try {
                val income = getExpensesUseCase(today, today)
                val total = income.sumOf { it.amount.toDouble() }.toString()
                val currency = income.firstOrNull()?.currency ?: "RUB"
                _state.value = ExpensesState.Content(income, total, currency)
            } catch (e: Exception) {
                val message = e.message ?: "Ошибка загрузки"
                _state.value = ExpensesState.Error(message)
                _event.emit(ExpensesEvent.ShowError(message))
            }
        }
    }

}