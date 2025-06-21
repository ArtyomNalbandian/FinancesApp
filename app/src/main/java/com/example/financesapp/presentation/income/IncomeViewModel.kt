package com.example.financesapp.presentation.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetIncomesUseCase
import com.example.financesapp.presentation.account.AccountState
import com.example.financesapp.presentation.history.HistoryIntent
import com.example.financesapp.utils.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class IncomeViewModelFactory(
    private val repository: GetIncomesUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IncomeViewModel::class.java)) {
            return IncomeViewModel(repository, networkMonitor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class IncomeViewModel(
    private val getIncomesUseCase: GetIncomesUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _state = MutableStateFlow<IncomeState>(IncomeState.Loading)
    val state: StateFlow<IncomeState> = _state

    private val _event = MutableSharedFlow<IncomeEvent>()
    val event: SharedFlow<IncomeEvent> = _event.asSharedFlow()

    private var isFirstLoad = true

    init {
        observeNetwork()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { connected ->
                if (connected && (isFirstLoad || _state.value is IncomeState.Error)) {
                    loadIncome()
                } else if (!connected) {
                    _state.value = IncomeState.Error("Нет подключения к интернету")
                    _event.emit(IncomeEvent.ShowError("Нет подключения к интернету"))
                }
            }
        }
    }

    fun retryLoad() {
        loadIncome()
    }

    private fun loadIncome() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = IncomeState.Loading
            try {
                val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                val income = getIncomesUseCase(today, today)
                val total = income.sumOf { it.amount.toDouble() }.toString()
                val currency = income.firstOrNull()?.currency ?: "RUB"
                _state.value = IncomeState.Content(income, total, currency)
                isFirstLoad = false
            } catch (e: Exception) {
                val message = e.message ?: "Ошибка загрузки"
                _state.value = IncomeState.Error(message)
                _event.emit(IncomeEvent.ShowError(message))
            }
        }
    }
}


//class IncomeViewModel(
//    private val getIncomesUseCase: GetIncomesUseCase,
//    private val networkMonitor: NetworkMonitor, // ← сюда
//) : ViewModel() {
//
//    private val _state = MutableStateFlow<IncomeState>(IncomeState.Loading)
//    val state: StateFlow<IncomeState> = _state
//
//    private val _event = MutableSharedFlow<IncomeEvent>()
//    val event: SharedFlow<IncomeEvent> = _event.asSharedFlow()
//
//    init {
//        observeNetwork()
//    }
//
//    private fun observeNetwork() {
//        viewModelScope.launch {
//            networkMonitor.isConnected.collect { connected ->
//                if (connected) {
//                    loadIncome() // ← заново загружаем
//                } else {
//                    _event.emit(IncomeEvent.ShowError("Нет подключения к интернету"))
//                    _state.value = IncomeState.Error("Нет подключения к интернету")
//                }
//            }
//        }
//    }
//
//    fun retryLoad() {
//        loadIncome()
//    }
//
//    private fun loadIncome() {
//        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
//
//        viewModelScope.launch(Dispatchers.IO) {
//            _state.value = IncomeState.Loading
//
//            // Проверка подключения
//            if (!networkMonitor.isConnected.value) {
//                _event.emit(IncomeEvent.ShowError("Нет подключения к интернету"))
//                _state.value = IncomeState.Error("Нет подключения к интернету")
//                return@launch
//            }
//
//            try {
//                val income = getIncomesUseCase(today, today)
//                val total = income.sumOf { it.amount.toDouble() }.toString()
//                val currency = income.firstOrNull()?.currency ?: "RUB"
//                _state.value = IncomeState.Content(income, total, currency)
//            } catch (e: Exception) {
//                val message = e.message ?: "Ошибка загрузки"
//                _state.value = IncomeState.Error(message)
//                _event.emit(IncomeEvent.ShowError(message))
//            }
//        }
//    }
//}


//class IncomeViewModel(
//    private val getIncomesUseCase: GetIncomesUseCase,
//) : ViewModel() {
//
//    private val _state = MutableStateFlow<IncomeState>(IncomeState.Loading)
//    val state: StateFlow<IncomeState> = _state
//
//    private val _event = MutableSharedFlow<IncomeEvent>()
//    val event: SharedFlow<IncomeEvent> = _event.asSharedFlow()
//
//    init {
//        loadIncome()
//    }
//
//    private fun loadIncome() {
//        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
//        viewModelScope.launch(Dispatchers.IO) {
//            _state.value = IncomeState.Loading
//            try {
//                val income = getIncomesUseCase(today, today)
//                val total = income.sumOf { it.amount.toDouble() }.toString()
//                val currency = income.firstOrNull()?.currency ?: "RUB"
//                _state.value = IncomeState.Content(income, total, currency)
//            } catch (e: Exception) {
//                val message = e.message ?: "Ошибка загрузки"
//                _state.value = IncomeState.Error(message)
//                _event.emit(IncomeEvent.ShowError(message))
//            }
//        }
//    }
//}
