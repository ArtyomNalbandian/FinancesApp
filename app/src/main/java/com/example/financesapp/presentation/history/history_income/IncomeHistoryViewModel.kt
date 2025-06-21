package com.example.financesapp.presentation.history.history_income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetIncomesUseCase
import com.example.financesapp.presentation.account.AccountState
import com.example.financesapp.presentation.history.HistoryIntent
import com.example.financesapp.presentation.history.history_expenses.ExpensesHistoryState
import com.example.financesapp.presentation.income.IncomeEvent
import com.example.financesapp.utils.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class IncomeHistoryViewModel(
    private val getIncomesUseCase: GetIncomesUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _state = MutableStateFlow<IncomeHistoryState>(IncomeHistoryState.Loading)
    val state: StateFlow<IncomeHistoryState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<IncomeEvent>()
    val event: SharedFlow<IncomeEvent> = _event.asSharedFlow()

    private var isFirstLoad = true

    init {
        observeNetwork(
            LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
            LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        )
    }

    private fun observeNetwork(startDate: String, endDate: String) {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { connected ->
                if (connected && (isFirstLoad || _state.value is IncomeHistoryState.Error)) {
                    loadHistory(startDate, endDate)
                } else if (!connected) {
                    _state.value = IncomeHistoryState.Error("Нет подключения к интернету")
                    _event.emit(IncomeEvent.ShowError("Нет подключения к интернету"))
                }
            }
        }
    }

    fun retryLoad() {
        loadHistory(
            LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
            LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        )
    }

    fun handleIntent(intent: HistoryIntent) {
        when (intent) {
            is HistoryIntent.LoadHistory -> loadHistory(intent.startDate, intent.endDate)
        }
    }

    private fun loadHistory(startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = IncomeHistoryState.Loading

            try {
                val historyList = getIncomesUseCase.invoke(
                    startDate, endDate
                )
                val total = historyList.sumOf { it.amount.toDouble() }
                _state.value = IncomeHistoryState.Content(historyList, "%,.2f ₽".format(total))
            } catch (e: Exception) {
                _state.value = IncomeHistoryState.Error(
                    "Ошибка: ${e.message}"
                )
            }
        }
    }

}

class IncomeHistoryViewModelFactory(
    private val usecase: GetIncomesUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IncomeHistoryViewModel::class.java)) {
            return IncomeHistoryViewModel(usecase, networkMonitor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
