package com.example.financesapp.data.remote.repository

import com.example.common.model.account.Account
import com.example.financesapp.data.mapper.toAccount
import com.example.financesapp.domain.repositories.AccountRepository
import com.example.financesapp.utils.retryRequest
import com.example.network.api.AccountsApi
import com.example.network.dto.account.AccountRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
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
    private val accountApi: AccountsApi
) : AccountRepository {

    private val _currentAccount = MutableStateFlow<Account?>(null)
    override val currentAccount: StateFlow<Account?> = _currentAccount.asStateFlow()
    private val accountFlow = MutableStateFlow<Account?>(null)

    override fun observeAccount(): Flow<Account> = accountFlow.filterNotNull()

    override suspend fun getAccount(): Account {
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
            accountApi.getAccounts().first().toAccount()
        }
    }

    override suspend fun updateAccount(accountId: Int, accountRequest: AccountRequestDto): Account {
        val updatedAccountDto = accountApi.updateAccount(
            id = accountId,
            accountRequest = accountRequest,
        )
        val updatedAccount = updatedAccountDto.toAccount()
        _currentAccount.value = updatedAccount
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
            accountApi.updateAccount(
                id = accountId,
                accountRequest = accountRequest
            ).toAccount()
        }
    }

    override suspend fun refreshAccount() {
        _currentAccount.value = getAccount()
    }

    override suspend fun getAccountById(accountId: String): Account {
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
            accountApi.getAccountById(accountId.toInt()).toAccount()
        }
    }
}
