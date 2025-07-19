package com.example.expenses.presentation.expenses_analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenses.domain.usecase.interfaces.GetExpensesUseCase
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
internal class ExpensesAnalysisViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesAnalysisState>(ExpensesAnalysisState.Loading)
    val state: StateFlow<ExpensesAnalysisState> = _state.asStateFlow()

    init {
        loadHistory(
            LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
            LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        )
    }

    fun handleIntent(intent: ExpensesAnalysisIntent) {
        when (intent) {
            is ExpensesAnalysisIntent.LoadAnalysis -> loadHistory(intent.startDate, intent.endDate)
        }
    }

    private fun loadHistory(startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ExpensesAnalysisState.Loading
            try {
                val historyList = getExpensesUseCase.invoke(
                    startDate, endDate
                )
                val total = historyList.sumOf { it.amount.toDouble() }
                _state.value = ExpensesAnalysisState.Content(historyList, total.toString())
            } catch (e: Exception) {
                _state.value = ExpensesAnalysisState.Error(
                    "Ошибка: ${e.message}"
                )
            }
        }
    }
}
