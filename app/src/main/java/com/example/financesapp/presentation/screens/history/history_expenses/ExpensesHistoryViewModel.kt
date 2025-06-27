package com.example.financesapp.presentation.screens.history.history_expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecases.interfaces.GetExpensesUseCase
import com.example.financesapp.presentation.screens.history.HistoryIntent
import com.example.financesapp.presentation.screens.income.IncomeEvent
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

class ExpensesHistoryViewModel(
    private val getExpensesUseCase: GetExpensesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesHistoryState>(ExpensesHistoryState.Loading)
    val state: StateFlow<ExpensesHistoryState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<IncomeEvent>()
    val event: SharedFlow<IncomeEvent> = _event.asSharedFlow()

    init {
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
            _state.value = ExpensesHistoryState.Loading
            try {
                val historyList = getExpensesUseCase.invoke(
                    startDate, endDate
                )
                val total = historyList.sumOf { it.amount.toDouble() }
                _state.value = ExpensesHistoryState.Content(historyList, "%,.2f ₽".format(total))
            } catch (e: Exception) {
                _state.value = ExpensesHistoryState.Error(
                    "Ошибка: ${e.message}"
                )
            }
        }
    }
}

class ExpensesHistoryViewModelFactory(
    private val usecase: GetExpensesUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesHistoryViewModel::class.java)) {
            return ExpensesHistoryViewModel(usecase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
