package com.example.financesapp.presentation.history.history_expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetExpensesUseCase
import com.example.financesapp.presentation.history.HistoryIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExpensesHistoryViewModel(
    private val getExpensesUseCase: GetExpensesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesHistoryState>(ExpensesHistoryState.Loading)
    val state: StateFlow<ExpensesHistoryState> = _state.asStateFlow()

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
        viewModelScope.launch {
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
    private val usecase: GetExpensesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesHistoryViewModel::class.java)) {
            return ExpensesHistoryViewModel(usecase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}