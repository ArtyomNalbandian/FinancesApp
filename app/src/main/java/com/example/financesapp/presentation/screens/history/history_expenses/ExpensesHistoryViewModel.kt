package com.example.financesapp.presentation.screens.history.history_expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecases.interfaces.GetExpensesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * ViewModel для экрана истории расходов.
 * Управляет:
 * - Загрузкой истории расходов за выбранный период
 * - Расчетом общей суммы
 * - Обработкой ошибок
 * Автоматически загружает данные за текущий месяц при инициализации.
 * @property getExpensesUseCase UseCase для получения расходов
 */
class ExpensesHistoryViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesHistoryState>(ExpensesHistoryState.Loading)
    val state: StateFlow<ExpensesHistoryState> = _state.asStateFlow()

    init {
        loadHistory(
            LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
            LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        )
    }

    fun handleIntent(intent: ExpensesHistoryIntent) {
        when (intent) {
            is ExpensesHistoryIntent.LoadHistory -> loadHistory(intent.startDate, intent.endDate)
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
