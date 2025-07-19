package com.example.expenses.presentation.expenses_edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.repository.AccountRepository
import com.example.categories.domain.repository.CategoriesRepository
import com.example.common.model.account.Account
import com.example.common.model.category.Category
import com.example.common.util.toLocalDateTime
import com.example.expenses.domain.usecase.interfaces.DeleteExpenseUseCase
import com.example.expenses.domain.usecase.interfaces.GetExpenseByIdUseCase
import com.example.expenses.domain.usecase.interfaces.UpdateExpenseUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpensesEditViewModel @Inject constructor(
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val accountRepository: AccountRepository,
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExpensesEditState())
    val state: StateFlow<ExpensesEditState> = _state.asStateFlow()

    fun handleIntent(intent: ExpensesEditIntent) {
        when (intent) {
            is ExpensesEditIntent.LoadTransaction -> loadTransaction(intent.expenseId)
            is ExpensesEditIntent.SelectAccount -> selectAccount(intent.account)
            is ExpensesEditIntent.SelectCategory -> selectCategory(intent.category)
            is ExpensesEditIntent.AmountChanged -> updateAmount(intent.amount)
            is ExpensesEditIntent.DateSelected -> updateDate(intent.date)
            is ExpensesEditIntent.TimeSelected -> updateTime(intent.time)
            is ExpensesEditIntent.CommentChanged -> updateComment(intent.comment)
            is ExpensesEditIntent.UpdateExpense -> updateTransaction()
            is ExpensesEditIntent.DeleteExpense -> deleteTransaction()
            is ExpensesEditIntent.ClearError -> clearError()
        }
    }

    fun loadInitialData(expenseId: Int) {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)

                val accountsDeferred = async { accountRepository.getAccount() }
                val categoriesDeferred =
                    async { categoriesRepository.getCategories().filter { !it.isIncome } }

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

    private fun loadTransaction(expenseId: Int) {
        viewModelScope.launch {
            try {
                val result = getExpenseByIdUseCase(expenseId)
                result.fold(
                    onSuccess = { expense ->
                        val selectedAccount = _state.value.account
                        val selectedCategory = _state.value.categories.find {
                            it.name == expense.categoryName
                        }

                        val parsedDateTime = expense.transactionDate.toLocalDateTime()
                        if (parsedDateTime == null) {
                            Log.d("testLog", "parsefucked --- ${expense.transactionDate}")
                        }

                        _state.value = _state.value.copy(
                            isLoading = false,
                            selectedAccount = selectedAccount,
                            selectedCategory = selectedCategory,
                            amount = expense.amount.removePrefix("-"),
                            selectedDateTime = expense.transactionDate.toLocalDateTime() ?: LocalDateTime.now(),
                            comment = expense.subtitle ?: ""
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

                val result = updateExpenseUseCase(
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

                val result = deleteExpenseUseCase(currentState.transactionId!!)

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

//class ExpensesEditViewModel @Inject constructor(
//    private val expenseId: Int,
//    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
//    private val updateExpenseUseCase: UpdateExpenseUseCase,
//    private val deleteExpenseUseCase: DeleteExpenseUseCase,
//    private val accountRepository: AccountRepository,
//    private val categoriesRepository: CategoriesRepository
//) : ViewModel() {
//
//    private val _state = MutableStateFlow(ExpensesEditState(transactionId = expenseId))
//    val state: StateFlow<ExpensesEditState> = _state.asStateFlow()
//
//    init {
//        loadInitialData()
//    }
//
//    fun handleIntent(intent: ExpensesEditIntent) {
//        when (intent) {
//            is ExpensesEditIntent.LoadTransaction -> loadTransaction()
//            is ExpensesEditIntent.SelectAccount -> selectAccount(intent.account)
//            is ExpensesEditIntent.SelectCategory -> selectCategory(intent.category)
//            is ExpensesEditIntent.AmountChanged -> updateAmount(intent.amount)
//            is ExpensesEditIntent.DateSelected -> updateDate(intent.date)
//            is ExpensesEditIntent.TimeSelected -> updateTime(intent.time)
//            is ExpensesEditIntent.CommentChanged -> updateComment(intent.comment)
//            is ExpensesEditIntent.UpdateExpense -> updateTransaction()
//            is ExpensesEditIntent.DeleteExpense -> deleteTransaction()
//            is ExpensesEditIntent.ClearError -> clearError()
//        }
//    }
//
//    private fun loadInitialData() {
//        viewModelScope.launch {
//            try {
//                _state.value = _state.value.copy(isLoading = true, error = null)
//
//                val accountsDeferred = async { accountRepository.getAccount() }
//                val categoriesDeferred =
//                    async { categoriesRepository.getCategories().filter { !it.isIncome } }
//
//                val account = accountsDeferred.await()
//                val categories = categoriesDeferred.await()
//
//                _state.value = _state.value.copy(
//                    account = account,
//                    categories = categories
//                )
//
//                loadTransaction()
//
//            } catch (e: Exception) {
//                _state.value = _state.value.copy(
//                    isLoading = false,
//                    error = "Ошибка загрузки данных: ${e.localizedMessage ?: e.message}"
//                )
//            }
//        }
//    }
//
//    private fun loadTransaction() {
//        viewModelScope.launch {
//            try {
//                val result = getExpenseByIdUseCase(expenseId)
//                result.fold(
//                    onSuccess = { expense ->
//                        val selectedAccount = _state.value.account
//                        val selectedCategory = _state.value.categories.find {
//                            it.name == expense.categoryName
//                        }
//
//                        _state.value = _state.value.copy(
//                            isLoading = false,
//                            selectedAccount = selectedAccount,
//                            selectedCategory = selectedCategory,
//                            amount = expense.amount.removePrefix("-"),
//                            selectedDateTime = expense.transactionDate.toLocalDateTime(),
//                            comment = expense.subtitle ?: ""
//                        )
//                        validateForm()
//                    },
//                    onFailure = { error ->
//                        _state.value = _state.value.copy(
//                            isLoading = false,
//                            error = "Ошибка загрузки транзакции: ${error.localizedMessage ?: error.message}"
//                        )
//                    }
//                )
//            } catch (e: Exception) {
//                _state.value = _state.value.copy(
//                    isLoading = false,
//                    error = "Ошибка загрузки транзакции: ${e.localizedMessage ?: e.message}"
//                )
//            }
//        }
//    }
//
//    private fun selectAccount(account: Account) {
//        _state.value = _state.value.copy(selectedAccount = account)
//        validateForm()
//    }
//
//    private fun selectCategory(category: Category) {
//        _state.value = _state.value.copy(selectedCategory = category)
//        validateForm()
//    }
//
//    private fun updateAmount(amount: String) {
//        val sanitizedAmount = amount.replace(",", ".")
//        val amountError = validateAmount(sanitizedAmount)
//
//        _state.value = _state.value.copy(
//            amount = sanitizedAmount,
//            amountError = amountError
//        )
//        validateForm()
//    }
//
//    private fun validateAmount(amount: String): String? {
//        return when {
//            amount.isBlank() -> "Сумма не может быть пустой"
//            amount.toDoubleOrNull() == null -> "Некорректная сумма"
//            amount.toDouble() <= 0 -> "Сумма должна быть больше нуля"
//            else -> null
//        }
//    }
//
//    private fun updateDate(date: LocalDate) {
//        val currentDateTime = _state.value.selectedDateTime
//        val newDateTime = currentDateTime.with(date)
//        _state.value = _state.value.copy(selectedDateTime = newDateTime)
//    }
//
//    private fun updateTime(time: LocalTime) {
//        val currentDateTime = _state.value.selectedDateTime
//        val newDateTime = currentDateTime.with(time)
//        _state.value = _state.value.copy(selectedDateTime = newDateTime)
//    }
//
//    private fun updateComment(comment: String) {
//        _state.value = _state.value.copy(comment = comment)
//    }
//
//    private fun validateForm() {
//        val currentState = _state.value
//        val isValid = currentState.selectedAccount != null &&
//                currentState.selectedCategory != null &&
//                currentState.amount.isNotBlank() &&
//                currentState.amountError == null
//
//        _state.value = currentState.copy(isFormValid = isValid)
//    }
//
//    private fun updateTransaction() {
//        val currentState = _state.value
//        if (!currentState.isFormValid) return
//
//        viewModelScope.launch {
//            try {
//                _state.value = _state.value.copy(isUpdating = true, error = null)
//
//                val result = updateExpenseUseCase(
//                    expenseId = expenseId,
//                    accountId = currentState.selectedAccount!!.id,
//                    categoryId = currentState.selectedCategory!!.id.toInt(),
//                    amount = currentState.amount, // Send positive amount - type is determined by category
//                    expenseDate = currentState.selectedDateTime.atOffset(ZoneOffset.UTC)
//                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
//                    comment = currentState.comment.takeIf { it.isNotBlank() }
//                )
//
//                result.fold(
//                    onSuccess = {
//                        _state.value = _state.value.copy(
//                            isUpdating = false,
//                            isUpdated = true
//                        )
//                    },
//                    onFailure = { error ->
//                        _state.value = _state.value.copy(
//                            isUpdating = false,
//                            error = "Ошибка обновления транзакции: ${error.localizedMessage ?: error.message}"
//                        )
//                    }
//                )
//            } catch (e: Exception) {
//                _state.value = _state.value.copy(
//                    isUpdating = false,
//                    error = "Ошибка обновления транзакции: ${e.localizedMessage ?: e.message}"
//                )
//            }
//        }
//    }
//
//    private fun deleteTransaction() {
//        viewModelScope.launch {
//            try {
//                _state.value = _state.value.copy(isDeleting = true, error = null)
//
//                val result = deleteExpenseUseCase(expenseId)
//
//                result.fold(
//                    onSuccess = {
//                        _state.value = _state.value.copy(
//                            isDeleting = false,
//                            isDeleted = true
//                        )
//                    },
//                    onFailure = { error ->
//                        _state.value = _state.value.copy(
//                            isDeleting = false,
//                            error = "Ошибка удаления транзакции: ${error.localizedMessage ?: error.message}"
//                        )
//                    }
//                )
//            } catch (e: Exception) {
//                _state.value = _state.value.copy(
//                    isDeleting = false,
//                    error = "Ошибка удаления транзакции: ${e.localizedMessage ?: e.message}"
//                )
//            }
//        }
//    }
//
//    private fun clearError() {
//        _state.value = _state.value.copy(error = null)
//    }
//}
