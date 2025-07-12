package com.example.incomes.presentation.incomes_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.repository.AccountRepository
import com.example.categories.domain.repository.CategoriesRepository
import com.example.common.model.account.Account
import com.example.common.model.category.Category
import com.example.common.util.toLocalDateTime
import com.example.incomes.domain.usecase.interfaces.DeleteIncomeUseCase
import com.example.incomes.domain.usecase.interfaces.GetIncomeByIdUseCase
import com.example.incomes.domain.usecase.interfaces.UpdateIncomeUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class IncomesEditViewModel @Inject constructor(
    private val getIncomeByIdUseCase: GetIncomeByIdUseCase,
    private val updateIncomeUseCase: UpdateIncomeUseCase,
    private val deleteIncomeUseCase: DeleteIncomeUseCase,
    private val accountRepository: AccountRepository,
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(IncomesEditState())
    val state: StateFlow<IncomesEditState> = _state.asStateFlow()

    fun handleIntent(intent: IncomesEditIntent) {
        when (intent) {
            is IncomesEditIntent.LoadTransaction -> loadTransaction(intent.expenseId)
            is IncomesEditIntent.SelectAccount -> selectAccount(intent.account)
            is IncomesEditIntent.SelectCategory -> selectCategory(intent.category)
            is IncomesEditIntent.AmountChanged -> updateAmount(intent.amount)
            is IncomesEditIntent.DateSelected -> updateDate(intent.date)
            is IncomesEditIntent.TimeSelected -> updateTime(intent.time)
            is IncomesEditIntent.CommentChanged -> updateComment(intent.comment)
            is IncomesEditIntent.UpdateIncome -> updateTransaction()
            is IncomesEditIntent.DeleteIncome -> deleteTransaction()
            is IncomesEditIntent.ClearError -> clearError()
        }
    }

    fun loadInitialData(expenseId: Int) {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)

                val accountsDeferred = async { accountRepository.getAccount() }
                val categoriesDeferred =
                    async { categoriesRepository.getCategories().filter { it.isIncome } }

                val account = accountsDeferred.await()
                val categories = categoriesDeferred.await()

                _state.value = _state.value.copy(
                    account = account,
                    categories = categories
                )
                _state.value = _state.value.copy(transactionId = expenseId)

                loadTransaction(expenseId)

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки данных: ${e.localizedMessage ?: e.message}"
                )
            }
        }
    }

    private fun loadTransaction(incomeId: Int) {
        viewModelScope.launch {
            try {
                val result = getIncomeByIdUseCase(incomeId)
                result.fold(
                    onSuccess = { income ->
                        val selectedAccount = _state.value.account
                        val selectedCategory = _state.value.categories.find {
                            it.name == income.categoryName
                        }

                        _state.value = _state.value.copy(
                            isLoading = false,
                            selectedAccount = selectedAccount,
                            selectedCategory = selectedCategory,
                            amount = income.amount.removePrefix("-"),
                            selectedDateTime = income.transactionDate.toLocalDateTime()
                                ?: LocalDateTime.now(),
                            comment = income.subtitle ?: ""
                        )
                        validateForm()
                    },
                    onFailure = { error ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = "Ошибка загрузки транзакции: ${error.localizedMessage ?: error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки транзакции: ${e.localizedMessage ?: e.message}"
                )
            }
        }
    }

    private fun selectAccount(account: Account) {
        _state.value = _state.value.copy(selectedAccount = account)
        validateForm()
    }

    private fun selectCategory(category: Category) {
        _state.value = _state.value.copy(selectedCategory = category)
        validateForm()
    }

    private fun updateAmount(amount: String) {
        val sanitizedAmount = amount.replace(",", ".")
        val amountError = validateAmount(sanitizedAmount)

        _state.value = _state.value.copy(
            amount = sanitizedAmount,
            amountError = amountError
        )
        validateForm()
    }

    private fun validateAmount(amount: String): String? {
        return when {
            amount.isBlank() -> "Сумма не может быть пустой"
            amount.toDoubleOrNull() == null -> "Некорректная сумма"
            amount.toDouble() <= 0 -> "Сумма должна быть больше нуля"
            else -> null
        }
    }

    private fun updateDate(date: LocalDate) {
        val currentDateTime = _state.value.selectedDateTime
        val newDateTime = currentDateTime.with(date)
        _state.value = _state.value.copy(selectedDateTime = newDateTime)
    }

    private fun updateTime(time: LocalTime) {
        val currentDateTime = _state.value.selectedDateTime
        val newDateTime = currentDateTime.with(time)
        _state.value = _state.value.copy(selectedDateTime = newDateTime)
    }

    private fun updateComment(comment: String) {
        _state.value = _state.value.copy(comment = comment)
    }

    private fun validateForm() {
        val currentState = _state.value
        val isValid = /*currentState.selectedAccount != null &&*/
            currentState.selectedCategory != null &&
                    currentState.amount.isNotBlank() &&
                    currentState.amountError == null

        _state.value = currentState.copy(isFormValid = isValid)
    }

    private fun updateTransaction() {
        val currentState = _state.value
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isUpdating = true, error = null)

                val result = updateIncomeUseCase(
                    expenseId = currentState.transactionId!!,
                    accountId = currentState.selectedAccount!!.id,
                    categoryId = currentState.selectedCategory!!.id.toInt(),
                    amount = currentState.amount, // Send positive amount - type is determined by category
                    expenseDate = currentState.selectedDateTime.atOffset(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    comment = currentState.comment.takeIf { it.isNotBlank() }
                )

                result.fold(
                    onSuccess = {
                        _state.value = _state.value.copy(
                            isUpdating = false,
                            isUpdated = true
                        )
                    },
                    onFailure = { error ->
                        _state.value = _state.value.copy(
                            isUpdating = false,
                            error = "Ошибка обновления транзакции: ${error.localizedMessage ?: error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isUpdating = false,
                    error = "Ошибка обновления транзакции: ${e.localizedMessage ?: e.message}"
                )
            }
        }
    }

    private fun deleteTransaction() {
        val currentState = _state.value
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isDeleting = true, error = null)

                val result = deleteIncomeUseCase(currentState.transactionId!!)

                result.fold(
                    onSuccess = {
                        _state.value = _state.value.copy(
                            isDeleting = false,
                            isDeleted = true
                        )
                    },
                    onFailure = { error ->
                        _state.value = _state.value.copy(
                            isDeleting = false,
                            error = "Ошибка удаления транзакции: ${error.localizedMessage ?: error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isDeleting = false,
                    error = "Ошибка удаления транзакции: ${e.localizedMessage ?: e.message}"
                )
            }
        }
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
