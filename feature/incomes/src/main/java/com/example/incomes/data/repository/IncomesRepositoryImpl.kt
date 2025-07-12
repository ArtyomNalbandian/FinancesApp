package com.example.incomes.data.repository

import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.common.model.income.Income
import com.example.incomes.data.mapper.toIncome
import com.example.incomes.domain.repository.IncomesRepository
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
 * Реализация [IncomesRepository] для работы с транзакциями через API.
 * Обеспечивает:
 * - Получение доходов за указанный период
 * - Автоматические повторы запросов при сетевых ошибках
 * - Фильтрацию и сортировку транзакций
 * - Преобразование DTO в доменные модели
 * @property transactionApi API для работы с транзакциями [TransactionApi]
 * @property getAccountUseCase UseCase для получения текущего аккаунта [GetAccountUseCase]
 * @constructor Создает репозиторий с внедренными зависимостями
 * @param transactionApi Реализация сетевого API транзакций
 * @param getAccountUseCase UseCase для получения ID аккаунта
 */
class IncomesRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi,
    private val getAccountUseCase: GetAccountUseCase
) : IncomesRepository {

    override suspend fun getIncomesByPeriod(startDate: String?, endDate: String?): List<Income> {
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
                .filter { it.categoryDto.isIncome }
                .sortedByDescending { it.transactionDate }
                .map { it.toIncome() }

        }
    }

    override suspend fun createIncome(
        accountId: Int,
        categoryId: Int,
        amount: String,
        incomeDate: String,
        comment: String?
    ): Result<Unit> {
        return try {
            val request = TransactionRequestDto(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = incomeDate,
                comment = comment
            )
            transactionApi.createTransaction(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getIncomeById(incomeId: Int): Result<Income> {
        return try {
            val transaction = transactionApi.getTransactionById(incomeId).toIncome()
            Result.success(transaction)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateIncome(
        incomeId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        incomeDate: String,
        comment: String?
    ): Result<Unit> {
        return try {
            val request = TransactionRequestDto(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = incomeDate,
                comment = comment
            )
            transactionApi.updateTransaction(incomeId, request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteIncome(incomeId: Int): Result<Unit> {
        return try {
            transactionApi.deleteTransaction(incomeId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getAccountId(): Int = withContext(Dispatchers.IO) {
        getAccountUseCase.invoke().id
    }
}
