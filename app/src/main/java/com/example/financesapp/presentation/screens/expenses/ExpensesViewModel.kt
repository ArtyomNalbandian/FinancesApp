package com.example.financesapp.presentation.screens.expenses

import android.util.Log
import androidx.lifecycle.ViewModel
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
import javax.inject.Inject

/**
 * ViewModel для управления состоянием экрана расходов.
 * Основные функции:
 * - Загрузка расходов за текущий день
 * - Расчет общей суммы расходов
 * - Обработка и отображение ошибок
 * @property getExpensesUseCase UseCase для получения расходов
 */
class ExpensesViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesState>(ExpensesState.Loading)
    val state: StateFlow<ExpensesState> = _state

    private val _event = MutableSharedFlow<ExpensesEvent>()
    val event: SharedFlow<ExpensesEvent> = _event.asSharedFlow()

    fun loadExpenses() {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ExpensesState.Loading
            try {
                val expenses = getExpensesUseCase(today, today)
                val total = expenses.sumOf { it.amount.toDouble() }.toString()
                val currency = expenses.firstOrNull()?.currency ?: "RUB"
                Log.d("testLog", "currency --- ${expenses.firstOrNull()?.currency}")
                _state.value = ExpensesState.Content(expenses, total, currency)
            } catch (e: Exception) {
                val message = e.message ?: "Ошибка загрузки"
                _state.value = ExpensesState.Error(message)
                _event.emit(ExpensesEvent.ShowError(message))
            }
        }
    }
}
