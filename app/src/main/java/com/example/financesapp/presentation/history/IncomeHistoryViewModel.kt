package com.example.financesapp.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetIncomesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IncomeHistoryViewModel(
    private val getIncomesUseCase: GetIncomesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<IncomeHistoryState>(IncomeHistoryState.Loading)
    val uiState: StateFlow<IncomeHistoryState> = _uiState.asStateFlow()


    fun handleIntent(intent: HistoryIntent) {
        when(intent) {
            is HistoryIntent.LoadHistory -> loadHistory(intent.startDate, intent.endDate)
        }
    }

    fun loadHistory(startDate: String, endDate: String) {
        viewModelScope.launch {
            _uiState.value = IncomeHistoryState.Loading

            try {
                val historyList = getIncomesUseCase.invoke(
                    startDate, endDate
                )
                val total = historyList.sumOf { it.amount.toDouble() }
                _uiState.value = IncomeHistoryState.Success(historyList, "%,.2f ₽".format(total))
            }
            catch (e: Exception) {
                _uiState.value = IncomeHistoryState.Error(
                    "Ошибка: ${e.message}"
                )
            }
        }
    }

}

class IncomeHistoryViewModelFactory(
    private val usecase: GetIncomesUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IncomeHistoryViewModel::class.java)) {
            return IncomeHistoryViewModel(usecase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}