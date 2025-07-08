package com.example.financesapp.presentation.screens.history.history_income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecases.interfaces.GetIncomesUseCase
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
class IncomeHistoryViewModel @Inject constructor(
    private val getIncomesUseCase: GetIncomesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<IncomeHistoryState>(IncomeHistoryState.Loading)
    val state: StateFlow<IncomeHistoryState> = _state.asStateFlow()

    init {
        loadHistory(
            LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
            LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        )
    }

    fun handleIntent(intent: IncomeHistoryIntent) {
        when (intent) {
            is IncomeHistoryIntent.LoadHistory -> loadHistory(intent.startDate, intent.endDate)
        }
    }

    private fun loadHistory(startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = IncomeHistoryState.Loading

            try {
                val historyList = getIncomesUseCase.invoke(
                    startDate, endDate
                )
                val total = historyList.sumOf { it.amount.toDouble() }
                _state.value = IncomeHistoryState.Content(historyList, "%,.2f".format(total))
            } catch (e: Exception) {
                _state.value = IncomeHistoryState.Error(
                    "Ошибка: ${e.message}"
                )
            }
        }
    }
}
