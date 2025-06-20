package com.example.financesapp.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetExpensesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpensesHistoryViewModel(
    private val getExpensesUseCase: GetExpensesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesHistoryState>(ExpensesHistoryState.Loading)
    val uiState: StateFlow<ExpensesHistoryState> = _uiState.asStateFlow()


    fun handleIntent(intent: HistoryIntent) {
        when(intent) {
            is HistoryIntent.LoadHistory -> loadHistory(intent.startDate, intent.endDate)
        }
    }

    fun loadHistory(startDate: String, endDate: String) {
        viewModelScope.launch {
            _uiState.value = ExpensesHistoryState.Loading

            try {
                val historyList = getExpensesUseCase.invoke(
                    startDate, endDate
                )
                val total = historyList.sumOf { it.amount.toDouble() }
                _uiState.value = ExpensesHistoryState.Success(historyList, "%,.2f ₽".format(total))
            }
            catch (e: Exception) {
                _uiState.value = ExpensesHistoryState.Error(
                    "Ошибка: ${e.message}"
                )
            }
        }
    }

}

class ExpensesHistoryViewModelFactory(
    private val usecase: GetExpensesUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesHistoryViewModel::class.java)) {
            return ExpensesHistoryViewModel(usecase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}