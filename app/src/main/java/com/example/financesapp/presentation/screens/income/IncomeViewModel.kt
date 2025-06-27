package com.example.financesapp.presentation.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecases.interfaces.GetIncomesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class IncomeViewModel @Inject constructor(
    private val getIncomesUseCase: GetIncomesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<IncomeState>(IncomeState.Loading)
    val state: StateFlow<IncomeState> = _state

    private val _event = MutableSharedFlow<IncomeEvent>()
    val event: SharedFlow<IncomeEvent> = _event.asSharedFlow()

    private var isFirstLoad = true

    init {
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
