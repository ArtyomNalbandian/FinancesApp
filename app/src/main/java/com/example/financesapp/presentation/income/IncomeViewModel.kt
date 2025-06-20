package com.example.financesapp.presentation.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetExpensesUseCase
import com.example.financesapp.domain.usecase.GetIncomesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class IncomeViewModelFactory(
    private val repository: GetIncomesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IncomeViewModel::class.java)) {
            return IncomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class IncomeViewModel(
    private val getIncomesUseCase: GetIncomesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<IncomeState>(IncomeState.Loading)
    val state: StateFlow<IncomeState> = _state

    private val _events = Channel<IncomeEvent>()
    val events = _events.receiveAsFlow()

    fun handleIntent(intent: IncomeIntent) {
        when (intent) {
            is IncomeIntent.CreateIncome -> {
                viewModelScope.launch {
                    _events.send(
                        IncomeEvent.NavigateToCreateIncomeScreen
                    )
                }
            }

            is IncomeIntent.EditIncome -> {
                viewModelScope.launch {
                    _events.send(
                        IncomeEvent.NavigateToEditIncomeScreen(intent.incomeId.toString())
                    )
                }
            }

            is IncomeIntent.OpenHistoryOfIncome -> {
                viewModelScope.launch {
                    _events.send(
                        IncomeEvent.NavigateToIncomeHistoryScreen
                    )
                }
            }

            is IncomeIntent.LoadIncome -> {
                loadIncome(intent.startDate, endDate = intent.endDate)
            }
        }
    }

    private fun loadIncome(startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = IncomeState.Loading
            try {
                val income = getIncomesUseCase(
                    startDate = startDate,
                    endDate = endDate
                )
                val total = income.sumOf { it.amount.toDouble() }.toString()
                val currency = income.firstOrNull()?.currency ?: "RUB"
                _state.value = IncomeState.Content(
                    income = income,
                    total = total,
                    currency = currency
                )
            } catch (e: Exception) {
                _state.value = IncomeState.Error(
                    message = e.message ?: "Не удалось загрузить расходы"
                )
                _events.send(IncomeEvent.ShowError("Ошибка загрузки данных"))
            }
        }
    }

}