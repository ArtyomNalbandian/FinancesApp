package com.example.financesapp.data.remote.repository

import com.example.financesapp.data.mapper.toAccount
import com.example.financesapp.data.remote.api.AccountsApi
import com.example.financesapp.domain.models.account.Account
import com.example.financesapp.domain.repositories.AccountRepository
import com.example.financesapp.utils.retryRequest
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountsApi
) : AccountRepository {
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
}
