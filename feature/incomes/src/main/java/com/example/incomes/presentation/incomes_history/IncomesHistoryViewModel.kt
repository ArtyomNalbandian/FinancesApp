package com.example.incomes.presentation.incomes_history

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
 * ViewModel для экрана истории доходов.
 * Управляет:
 * - Загрузкой истории доходов за выбранный период
 * - Расчетом общей суммы
 * - Обработкой ошибок
 * Автоматически загружает данные за текущий месяц при инициализации.
 * @property getIncomesUseCase UseCase для получения доходов
 */
internal class IncomesHistoryViewModel @Inject constructor(
    private val getIncomesUseCase: GetIncomesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<IncomesHistoryState>(IncomesHistoryState.Loading)
    val state: StateFlow<IncomesHistoryState> = _state.asStateFlow()

    init {
        loadHistory(
            LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
            LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        )
    }

    fun handleIntent(intent: IncomesHistoryIntent) {
        when (intent) {
            is IncomesHistoryIntent.LoadHistory -> loadHistory(intent.startDate, intent.endDate)
        }
    }

    private fun loadHistory(startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = IncomesHistoryState.Loading

            try {
                val historyList = getIncomesUseCase.invoke(
                    startDate, endDate
                )
                val total = historyList.sumOf { it.amount.toDouble() }
                _state.value = IncomesHistoryState.Content(historyList, "%,.2f".format(total))
            } catch (e: Exception) {
                _state.value = IncomesHistoryState.Error(
                    "Ошибка: ${e.message}"
                )
            }
        }
    }
}
