package com.example.database.sync

import android.content.Context
import com.example.database.dao.AccountDao
import com.example.database.dao.CategoryDao
import com.example.database.dao.TransactionDao
import com.example.database.entity.AccountEntity
import com.example.database.entity.TransactionEntity
import com.example.network.api.AccountsApi
import com.example.network.api.CategoriesApi
import com.example.network.api.TransactionApi
import com.example.network.dto.account.AccountRequestDto
import com.example.network.dto.transaction.TransactionRequestDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SyncManager {
    fun syncAll(
        context: Context,
        accountDao: AccountDao,
        categoryDao: CategoryDao,
        transactionDao: TransactionDao,
        accountsApi: AccountsApi,
        categoriesApi: CategoriesApi,
        transactionApi: TransactionApi,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            // Синхронизация аккаунта
            val dirtyAccount = accountDao.getDirtyAccount()
            dirtyAccount?.let {
                try {
                    accountsApi.updateAccount(it.id, it.toAccountRequestDto())
                    accountDao.update(it.copy(isDirty = false))
                } catch (_: Exception) {}
            }
            // Синхронизация категорий
            // Тут синхронизация не нужна
//            val dirtyCategories = categoryDao.getDirty()
//            dirtyCategories.forEach { category ->
//                try {
//                    categoriesApi.updateCategory(category.id, /* CategoryRequestDto */)
//                    categoryDao.update(category.copy(isDirty = false))
//                } catch (_: Exception) {}
//            }
            // Синхронизация транзакций
            val dirtyTransactions = transactionDao.getDirty()
            dirtyTransactions.forEach { transaction ->
                try {
                    transactionApi.updateTransaction(transaction.id, transaction.toTransactionRequestDto())
                    transactionDao.update(transaction.copy(isDirty = false))
                } catch (_: Exception) {}
            }
        }

    }
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
