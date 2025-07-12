package com.example.incomes.presentation.incomes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.incomes.domain.usecase.interfaces.GetIncomesUseCase
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
 * ViewModel для управления состоянием экрана доходов.
 * Основные функции:
 * - Загрузка доходов за текущий день
 * - Расчет общей суммы доходов
 * - Обработка и отображение ошибок
 * @property getIncomesUseCase UseCase для получения доходов
 */
internal class IncomesViewModel @Inject constructor(
    private val getIncomesUseCase: GetIncomesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<IncomesState>(IncomesState.Loading)
    val state: StateFlow<IncomesState> = _state

    private val _event = MutableSharedFlow<IncomesEvent>()
    val event: SharedFlow<IncomesEvent> = _event.asSharedFlow()

    fun loadIncome() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = IncomesState.Loading
            try {
                val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                val income = getIncomesUseCase(today, today)
                val total = income.sumOf { it.amount.toDouble() }.toString()
                val currency = income.firstOrNull()?.currency ?: "RUB"
                _state.value = IncomesState.Content(income, total, currency)
            } catch (e: Exception) {
                val message = e.message ?: "Ошибка загрузки"
                _state.value = IncomesState.Error(message)
                _event.emit(IncomesEvent.ShowError(message))
            }
        }
    }
}
