package com.example.financesapp.data.remote.repository

import com.example.financesapp.data.mapper.toExpense
import com.example.financesapp.data.mapper.toIncome
import com.example.financesapp.data.remote.api.TransactionApi
import com.example.financesapp.domain.models.expenses.Expense
import com.example.financesapp.domain.models.income.Income
import com.example.financesapp.domain.repositories.TransactionRepository
import com.example.financesapp.domain.usecases.interfaces.GetAccountsUseCase
import com.example.financesapp.utils.retryRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Реализация [TransactionRepository] для работы с транзакциями через API.
 * Обеспечивает:
 * - Получение расходов/доходов за указанный период
 * - Автоматические повторы запросов при сетевых ошибках
 * - Фильтрацию и сортировку транзакций
 * - Преобразование DTO в доменные модели
 * @property transactionApi API для работы с транзакциями [TransactionApi]
 * @property getAccountsUseCase UseCase для получения текущего аккаунта [GetAccountsUseCase]
 * @constructor Создает репозиторий с внедренными зависимостями
 * @param transactionApi Реализация сетевого API транзакций
 * @param getAccountsUseCase UseCase для получения ID аккаунта
 */
class TransactionRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi,
    private val getAccountsUseCase: GetAccountsUseCase
) : TransactionRepository {
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
                .filter { !it.category.isIncome }
                .sortedByDescending { it.transactionDate }
                .map { it.toExpense() }
        }
    }

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
                .filter { it.category.isIncome }
                .sortedByDescending { it.transactionDate }
                .map { it.toIncome() }
        }
    }

    private suspend fun getAccountId(): Int = withContext(Dispatchers.IO) {
        getAccountsUseCase.invoke().id
    }
}
