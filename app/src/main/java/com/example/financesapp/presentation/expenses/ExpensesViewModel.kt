package com.example.financesapp.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financesapp.domain.usecase.GetExpensesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SpendingViewModelFactory(
    private val repository: GetExpensesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesViewModel::class.java)) {
            return ExpensesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class ExpensesViewModel(
    private val getExpensesUseCase: GetExpensesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ExpensesScreenState>(ExpensesScreenState.Loading)
    val state: StateFlow<ExpensesScreenState> = _state

    private val _events = Channel<ExpensesEvent>()
    val events = _events.receiveAsFlow()

    fun handleIntent(intent: ExpensesIntent) {
        when (intent) {
            is ExpensesIntent.CreateExpense -> {
                viewModelScope.launch {
                    _events.send(
                        ExpensesEvent.NavigateToCreateExpenseScreen
                    )
                }
            }
            is ExpensesIntent.EditExpense -> {
                viewModelScope.launch {
                    _events.send(
                        ExpensesEvent.NavigateToEditExpenseScreen(intent.expenseId.toString())
                    )
                }
            }
            is ExpensesIntent.OpenHistoryOfExpenses -> {
                viewModelScope.launch {
                    _events.send(
                        ExpensesEvent.NavigateToExpensesHistoryScreen
                    )
                }
            }
        }
    }

    init {
        val today = LocalDate.now()
        val formattedDate = today.format(DateTimeFormatter.ISO_DATE)
        loadExpenses(accountId = 1, startDate = formattedDate, endDate = formattedDate)
    }

    private fun loadExpenses(accountId: Int, startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ExpensesScreenState.Loading
            try {
                val expenses = getExpensesUseCase(
                    accountId = accountId,
                    startDate = startDate,
                    endDate = endDate
                )
                val total = expenses.sumOf { it.amount.toDouble() }.toString()
                val currency = expenses.firstOrNull()?.currency ?: "RUB"
                _state.value = ExpensesScreenState.Content(
                    expenses = expenses,
                    total = total,
                    currency = currency
                )
            } catch (e: Exception) {
                _state.value = ExpensesScreenState.Error(
                    message = e.message ?: "Не удалось загрузить расходы"
                )
                _events.send(ExpensesEvent.ShowError("Ошибка загрузки данных"))
            }
        }
    }

}