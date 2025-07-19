package com.example.incomes.presentation.incomes_analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.incomes.domain.usecase.interfaces.GetIncomesUseCase
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
 * @property getIncomesUseCase UseCase для получения расходов
 */
internal class IncomesAnalysisViewModel @Inject constructor(
    private val getIncomesUseCase: GetIncomesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<IncomesAnalysisState>(IncomesAnalysisState.Loading)
    val state: StateFlow<IncomesAnalysisState> = _state.asStateFlow()

    init {
        loadHistory(
            LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
            LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        )
    }

    fun handleIntent(intent: IncomesAnalysisIntent) {
        when (intent) {
            is IncomesAnalysisIntent.LoadAnalysis -> loadHistory(intent.startDate, intent.endDate)
        }
    }

    private fun loadHistory(startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = IncomesAnalysisState.Loading
            try {
                val historyList = getIncomesUseCase.invoke(
                    startDate, endDate
                )
                val total = historyList.sumOf { it.amount.toDouble() }
                _state.value = IncomesAnalysisState.Content(historyList, total.toString())
            } catch (e: Exception) {
                _state.value = IncomesAnalysisState.Error(
                    "Ошибка: ${e.message}"
                )
            }
        }
    }
}
