package com.example.financesapp.presentation.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetIncomesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    private val _event = Channel<IncomeEvent>()
    val event = _event.receiveAsFlow()

    init {
        loadIncome()
    }

    private fun loadIncome() {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = IncomeState.Loading
            try {
                delay(1000)
                val income = getIncomesUseCase(today, today)
                val total = income.sumOf { it.amount.toDouble() }.toString()
                val currency = income.firstOrNull()?.currency ?: "RUB"
                _state.value = IncomeState.Content(income, total, currency)
            } catch (e: Exception) {
                val message = e.message ?: "Ошибка загрузки"
                _state.value = IncomeState.Error(message)
                _event.send(IncomeEvent.ShowError(message))
            }
        }
    }
}
