package com.example.expenses.data.repository

import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.common.model.expense.Expense
import com.example.database.dao.CategoryDao
import com.example.database.dao.TransactionDao
import com.example.database.entity.TransactionEntity
import com.example.expenses.data.mapper.toExpense
import com.example.expenses.data.mapper.toTransactionEntity
import com.example.expenses.domain.repository.ExpensesRepository
import com.example.network.api.TransactionApi
import com.example.network.dto.transaction.TransactionRequestDto
import com.example.network.util.retryRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
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
    private val getAccountUseCase: GetAccountUseCase,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : ExpensesRepository {
    override suspend fun getExpensesByPeriod(startDate: String?, endDate: String?): List<Expense> {
        return try {
            val accountId = getAccountUseCase.invoke().id
            val transactions = transactionApi.getTransactionsByPeriod(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate
            )
            val entities = transactions
                .filter { !it.categoryDto.isIncome }
                .map { it.toTransactionEntity() }
            transactionDao.insertAll(entities)
            val categories = categoryDao.getAll().first().associateBy { it.id }
            entities.mapNotNull { entity ->
                categories[entity.categoryId]?.let { category ->
                    entity.toExpense(category, getAccountUseCase.invoke().currency)
                }
            }
        } catch (e: Exception) {
            val categories = categoryDao.getAll().first().associateBy { it.id }
            return if (startDate != null && endDate != null && startDate == endDate) {
                transactionDao.getByDate(startDate)
                    .filter { categories[it.categoryId]?.isIncome == false }
                    .mapNotNull { entity ->
                        categories[entity.categoryId]?.let { category ->
                            entity.toExpense(category, getAccountUseCase.invoke().currency)
                        }
                    }
            } else if (startDate != null && endDate != null) {
                transactionDao.getByDateRange(startDate, endDate)
                    .filter { categories[it.categoryId]?.isIncome == false }
                    .mapNotNull { entity ->
                        categories[entity.categoryId]?.let { category ->
                            entity.toExpense(category, getAccountUseCase.invoke().currency)
                        }
                    }
            } else {
                emptyList()
            }
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
            val entity = TransactionEntity(
                id = 0,
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = expenseDate,
                comment = comment,
                createdAt = expenseDate,
                updatedAt = expenseDate,
                isDirty = true
            )
            transactionDao.insertAll(listOf(entity))
            Result.success(Unit)
        }
    }

    override suspend fun getExpenseById(expenseId: Int): Result<Expense> {
        return try {
            val transaction = transactionApi.getTransactionById(expenseId)
            val category = categoryDao.getAll().first().find { it.id == transaction.categoryDto.id }
            if (category != null && !category.isIncome) {
                Result.success(transaction.toTransactionEntity().toExpense(category, getAccountUseCase.invoke().currency))
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            // fallback на локальную БД
            val entity = transactionDao.getAll().first().find { it.id == expenseId }
            val category = entity?.let { categoryDao.getAll().first().find { cat -> cat.id == it.categoryId } }
            if (entity != null && category != null && !category.isIncome) {
                Result.success(entity.toExpense(category, getAccountUseCase.invoke().currency))
            } else {
                Result.failure(e)
            }
        }
    }

//    override suspend fun getExpenseById(expenseId: Int): Result<Expense> {
//        return try {
//            val transaction = transactionApi.getTransactionById(expenseId).toExpense()
//            Result.success(transaction)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

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
            // fallback: помечаем локально как dirty
            val entity = transactionDao.getAll().first().find { it.id == expenseId }
            if (entity != null) {
                val updated = entity.copy(
                    accountId = accountId,
                    categoryId = categoryId,
                    amount = amount,
                    transactionDate = expenseDate,
                    comment = comment,
                    updatedAt = expenseDate,
                    isDirty = true
                )
                transactionDao.update(updated)
                Result.success(Unit)
            } else {
                Result.failure(e)
            }
        }
    }

//    override suspend fun updateExpense(
//        expenseId: Int,
//        accountId: Int,
//        categoryId: Int,
//        amount: String,
//        expenseDate: String,
//        comment: String?
//    ): Result<Unit> {
//        return try {
//            val request = TransactionRequestDto(
//                accountId = accountId,
//                categoryId = categoryId,
//                amount = amount,
//                transactionDate = expenseDate,
//                comment = comment
//            )
//            transactionApi.updateTransaction(expenseId, request)
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

    override suspend fun deleteExpense(expenseId: Int): Result<Unit> {
        return try {
            transactionApi.deleteTransaction(expenseId)
            // Можно удалить из локальной БД, если хотите
            Result.success(Unit)
        } catch (e: Exception) {
            // fallback: помечаем как dirty (например, можно добавить отдельное поле isDeleted)
            // или просто не удалять, а синхронизировать позже
            Result.failure(e)
        }
    }

//    override suspend fun deleteExpense(expenseId: Int): Result<Unit> {
//        return try {
//            transactionApi.deleteTransaction(expenseId)
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

//    private suspend fun getAccountId(): Int = withContext(Dispatchers.IO) {
//        getAccountUseCase.invoke().id
//    }
}
