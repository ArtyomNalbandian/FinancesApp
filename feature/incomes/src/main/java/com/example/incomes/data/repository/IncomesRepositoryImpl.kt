package com.example.incomes.data.repository

import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.common.model.income.Income
import com.example.database.dao.CategoryDao
import com.example.database.dao.TransactionDao
import com.example.database.entity.TransactionEntity
import com.example.incomes.data.mapper.toIncome
import com.example.incomes.data.mapper.toTransactionEntity
import com.example.incomes.domain.repository.IncomesRepository
import com.example.network.api.TransactionApi
import com.example.network.dto.transaction.TransactionRequestDto
import kotlinx.coroutines.flow.first
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
    private val getAccountUseCase: GetAccountUseCase,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : IncomesRepository {
    override suspend fun getIncomesByPeriod(startDate: String?, endDate: String?): List<Income> {
        return try {
            val accountId = getAccountUseCase.invoke().id
            val transactions = transactionApi.getTransactionsByPeriod(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate
            )
            val entities = transactions
                .filter { it.categoryDto.isIncome }
                .map { it.toTransactionEntity() }
            transactionDao.insertAll(entities)
            transactions
                .filter { it.categoryDto.isIncome }
                .map { it.toIncome() }
        } catch (e: Exception) {
            val categories = categoryDao.getAll().first().associateBy { it.id }
            return if (startDate != null && endDate != null && startDate == endDate) {
                transactionDao.getByDate(startDate)
                    .filter { categories[it.categoryId]?.isIncome == true }
                    .mapNotNull { entity ->
                        categories[entity.categoryId]?.let { category ->
                            entity.toIncome(category, getAccountUseCase.invoke().currency)
                        }
                    }
            } else if (startDate != null && endDate != null) {
                transactionDao.getByDateRange(startDate, endDate)
                    .filter { categories[it.categoryId]?.isIncome == true }
                    .mapNotNull { entity ->
                        categories[entity.categoryId]?.let { category ->
                            entity.toIncome(category, getAccountUseCase.invoke().currency)
                        }
                    }
            } else {
                emptyList()
            }
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
            val entity = TransactionEntity(
                id = 0,
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = incomeDate,
                comment = comment,
                createdAt = incomeDate,
                updatedAt = incomeDate,
                isDirty = true
            )
            transactionDao.insertAll(listOf(entity))
            Result.success(Unit)
        }
    }

    override suspend fun getIncomeById(incomeId: Int): Result<Income> {
        return try {
            val transaction = transactionApi.getTransactionById(incomeId)
            val category = categoryDao.getAll().first().find { it.id == transaction.categoryDto.id }
            if (category != null && category.isIncome) {
                Result.success(
                    transaction.toTransactionEntity()
                        .toIncome(category, getAccountUseCase.invoke().currency)
                )
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            val entity = transactionDao.getAll().first().find { it.id == incomeId }
            val category =
                entity?.let { categoryDao.getAll().first().find { cat -> cat.id == it.categoryId } }
            if (entity != null && category != null && !category.isIncome) {
                Result.success(entity.toIncome(category, getAccountUseCase.invoke().currency))
            } else {
                Result.failure(e)
            }
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
            val entity = transactionDao.getAll().first().find { it.id == incomeId }
            if (entity != null) {
                val updated = entity.copy(
                    accountId = accountId,
                    categoryId = categoryId,
                    amount = amount,
                    transactionDate = incomeDate,
                    comment = comment,
                    updatedAt = incomeDate,
                    isDirty = true
                )
                transactionDao.update(updated)
                Result.success(Unit)
            } else {
                Result.failure(e)
            }
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
}
