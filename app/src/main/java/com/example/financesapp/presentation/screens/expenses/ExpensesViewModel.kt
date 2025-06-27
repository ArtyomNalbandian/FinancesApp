package com.example.financesapp.presentation.screens.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecases.interfaces.GetExpensesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExpensesViewModelFactory(
    private val repository: GetExpensesUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesViewModel::class.java)) {
            return ExpensesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class ExpensesViewModel(
    private val getExpensesUseCase: GetExpensesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesState>(ExpensesState.Loading)
    val state: StateFlow<ExpensesState> = _state

    private val _event = MutableSharedFlow<ExpensesEvent>()
    val event: SharedFlow<ExpensesEvent> = _event.asSharedFlow()

    private var isFirstLoad = true

    init {
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