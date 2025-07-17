package com.example.account.data.repository

import com.example.account.data.mapper.toAccount
import com.example.account.data.mapper.toAccountEntity
import com.example.account.domain.repository.AccountRepository
import com.example.common.model.account.Account
import com.example.database.dao.AccountDao
import com.example.network.api.AccountsApi
import com.example.network.dto.account.AccountRequestDto
import com.example.network.util.retryRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Реализация [AccountRepository] для работы со счетом через API.
 * Предоставляет методы для получения информации о счете с обработкой ошибок и автоматическими повторами запросов.
 * @property accountApi API-интерфейс [AccountsApi]
 * @constructor Создает экземпляр репозитория [AccountsApi]
 * @param accountApi Реализация сетевого API для работы со счетами
 */
class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountsApi,
    private val accountDao: AccountDao
) : AccountRepository {

    override suspend fun getAccount(): Account {
        return try {
            val dto = accountApi.getAccounts().first()
            val entity = dto.toAccountEntity()
            accountDao.insert(entity)
            dto.toAccount()
        } catch (e: Exception) {
            // fallback: local account
            accountDao.getAccount().first()?.toAccount()
                ?: throw e // ничего в базе — прокидываем ошибку
        }
    }

    override suspend fun updateAccount(accountId: Int, accountRequest: AccountRequestDto): Account {
        return try {
            val updated = accountApi.updateAccount(accountId, accountRequest)
            val entity = updated.toAccountEntity().copy(isDirty = false)
            accountDao.insert(entity)
            updated.toAccount()
        } catch (e: Exception) {
            // fallback: mark entity as dirty
            val local = accountDao.getAccount().first()
            if (local != null) {
                val dirty = local.copy(
                    name = accountRequest.name,
                    balance = accountRequest.balance,
                    currency = accountRequest.currency,
                    isDirty = true
                )
                accountDao.update(dirty)
                dirty.toAccount()
            } else {
                throw e
            }
        }
    }
    override suspend fun syncAccountIfNeeded() {
        val dirtyAccount = accountDao.getDirtyAccount()
        if (dirtyAccount != null) {
            try {
                val request = AccountRequestDto(
                    name = dirtyAccount.name,
                    balance = dirtyAccount.balance,
                    currency = dirtyAccount.currency
                )
                val updated = accountApi.updateAccount(dirtyAccount.id, request)
                val synced = updated.toAccountEntity().copy(isDirty = false)
                accountDao.insert(synced)
            } catch (e: Exception) {
                // не удалось синхронизировать — просто замолчи, подождем следующего раза
            }
        }
    }
}
//class AccountRepositoryImpl @Inject constructor(
//    private val accountApi: AccountsApi
//) : AccountRepository {
//
//    override suspend fun getAccount(): Account {
//        return retryRequest(
//            shouldRetry = { throwable ->
//                when (throwable) {
//                    is UnknownHostException -> false
//                    is IOException -> true
//                    is HttpException -> throwable.code() in 500..599
//                    else -> false
//                }
//            }
//        ) {
//            accountApi.getAccounts().first().toAccount()
//        }
//    }
//
//    override suspend fun updateAccount(accountId: Int, accountRequest: AccountRequestDto): Account {
//        return retryRequest(
//            shouldRetry = { throwable ->
//                when (throwable) {
//                    is UnknownHostException -> false
//                    is IOException -> true
//                    is HttpException -> throwable.code() in 500..599
//                    else -> false
//                }
//            }
//        ) {
//            accountApi.updateAccount(
//                id = accountId,
//                accountRequest = accountRequest
//            ).toAccount()
//        }
//    }
//}
