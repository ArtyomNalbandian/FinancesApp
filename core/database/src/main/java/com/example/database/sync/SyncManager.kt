package com.example.database.sync

import android.content.Context
import com.example.database.dao.AccountDao
import com.example.database.dao.TransactionDao
import com.example.database.entity.AccountEntity
import com.example.database.entity.TransactionEntity
import com.example.network.api.AccountsApi
import com.example.network.api.TransactionApi
import com.example.network.dto.account.AccountRequestDto
import com.example.network.dto.transaction.TransactionDto
import com.example.network.dto.transaction.TransactionRequestDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SyncManager {
    fun syncAll(
        context: Context,
        accountDao: AccountDao,
        transactionDao: TransactionDao,
        accountsApi: AccountsApi,
        transactionApi: TransactionApi,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            // Синхронизация аккаунта
            val dirtyAccount = accountDao.getDirtyAccount()
            dirtyAccount?.let {
                try {
                    accountsApi.updateAccount(it.id, it.toAccountRequestDto())
                    accountDao.update(it.copy(isDirty = false))
                } catch (_: Exception) {
                }
            }
            // Синхронизация транзакций
            val dirtyTransactions = transactionDao.getDirty()
            dirtyTransactions.forEach { transaction ->
                try {
                    if (transaction.id == 0) {
                        val response =
                            transactionApi.createTransaction(transaction.toTransactionRequestDto())
                        transactionDao.deleteById(transaction.id) // удалить временную запись
                        transactionDao.insertAll(
                            listOf(
                                response.toTransactionEntity().copy(isDirty = false)
                            )
                        )
                    } else {
                        transactionApi.updateTransaction(
                            transaction.id,
                            transaction.toTransactionRequestDto()
                        )
                        transactionDao.update(transaction.copy(isDirty = false))
                    }
                } catch (_: Exception) {
                }
            }
        }
    }
}

// Стыдно за костыли но мне пришлось:(
fun TransactionDto.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDirty = false
    )
}

fun AccountEntity.toAccountRequestDto(): AccountRequestDto {
    return AccountRequestDto(
        name = name,
        balance = balance,
        currency = currency
    )
}

fun TransactionEntity.toTransactionRequestDto(): TransactionRequestDto {
    return TransactionRequestDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )
}
