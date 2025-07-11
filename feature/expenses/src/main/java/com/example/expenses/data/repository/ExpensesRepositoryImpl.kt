package com.example.expenses.data.repository

import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.common.model.expense.Expense
import com.example.expenses.data.mapper.toExpense
import com.example.expenses.domain.repository.ExpensesRepository
import com.example.network.api.TransactionApi
import com.example.network.dto.transaction.TransactionRequestDto
import com.example.network.util.retryRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Реализация [ExpensesRepository] для работы с расходами через API.
 * Обеспечивает:
 * - Получение расходов за указанный период
 * - Автоматические повторы запросов при сетевых ошибках
 * - Фильтрацию и сортировку транзакций
 * - Преобразование DTO в доменные модели
 * @property transactionApi API для работы с транзакциями [TransactionApi]
 * @property getAccountUseCase UseCase для получения текущего аккаунта [GetAccountUseCase]
 * @constructor Создает репозиторий с внедренными зависимостями
 * @param transactionApi Реализация сетевого API транзакций
 * @param getAccountUseCase UseCase для получения ID аккаунта
 */
class ExpensesRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi,
    private val getAccountUseCase: GetAccountUseCase
) : ExpensesRepository {
    override suspend fun getExpensesByPeriod(startDate: String?, endDate: String?): List<Expense> {
        return retryRequest(
            shouldRetry = { throwable ->
                when (throwable) {
                    is UnknownHostException -> false
                    is IOException -> true
                    is HttpException -> throwable.code() in 500..599
                    else -> false
                }
            }
        ) {
            transactionApi.getTransactionsByPeriod(
                accountId = getAccountId(),
                startDate = startDate,
                endDate = endDate
            )
                .filter { !it.categoryDto.isIncome }
                .sortedByDescending { it.transactionDate }
                .map { it.toExpense() }
        }
    }

    override suspend fun createExpense(
        accountId: Int,
        categoryId: Int,
        amount: String,
        expenseDate: String,
        comment: String?
    ): Result<Unit> {
        return try {
            val request = TransactionRequestDto(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = expenseDate,
                comment = comment
            )
            transactionApi.createTransaction(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getExpenseById(expenseId: Int): Result<Expense> {
        return try {
            val transaction = transactionApi.getTransactionById(expenseId).toExpense()
            Result.success(transaction)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateExpense(
        expenseId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        expenseDate: String,
        comment: String?
    ): Result<Unit> {
        return try {
            val request = TransactionRequestDto(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = expenseDate,
                comment = comment
            )
            transactionApi.updateTransaction(expenseId, request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getAccountId(): Int = withContext(Dispatchers.IO) {
        getAccountUseCase.invoke().id
    }
}
