package com.example.account.data.repository

import com.example.account.data.mapper.toAccount
import com.example.account.data.mapper.toAccountEntity
import com.example.account.domain.repository.AccountRepository
import com.example.common.model.account.Account
import com.example.database.dao.AccountDao
import com.example.network.api.AccountsApi
import com.example.network.dto.account.AccountRequestDto
import kotlinx.coroutines.flow.first
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
            accountDao.getAccount().first().toAccount()
        }
    }

    override suspend fun updateAccount(accountId: Int, accountRequest: AccountRequestDto): Account {
        return try {
            val updated = accountApi.updateAccount(accountId, accountRequest)
            val entity = updated.toAccountEntity().copy(isDirty = false)
            accountDao.insert(entity)
            updated.toAccount()
        } catch (e: Exception) {
            val local = accountDao.getAccount().first()
            run {
                val dirty = local.copy(
                    name = accountRequest.name,
                    balance = accountRequest.balance,
                    currency = accountRequest.currency,
                    isDirty = true
                )
                accountDao.update(dirty)
                dirty.toAccount()
            }
        }
    }
}
