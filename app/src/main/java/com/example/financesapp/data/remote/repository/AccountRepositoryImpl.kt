package com.example.financesapp.data.remote.repository

import com.example.financesapp.data.mapper.toAccount
import com.example.financesapp.data.remote.api.ApiService
import com.example.financesapp.domain.models.account.Account
import com.example.financesapp.domain.repositories.AccountRepository
import com.example.financesapp.utils.retryRequest
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class AccountRepositoryImpl(
    private val apiService: ApiService
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
            apiService.getAccounts().first().toAccount()
        }
    }
}
