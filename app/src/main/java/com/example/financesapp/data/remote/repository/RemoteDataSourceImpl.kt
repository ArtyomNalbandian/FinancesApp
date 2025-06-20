package com.example.financesapp.data.remote.repository

import android.util.Log
import com.example.financesapp.data.remote.api.ApiService
import com.example.financesapp.data.remote.models.account.AccountDto
import com.example.financesapp.data.remote.models.account.AccountHistoryResponse
import com.example.financesapp.data.remote.models.account.AccountRequest
import com.example.financesapp.data.remote.models.account.AccountResponse
import com.example.financesapp.data.remote.models.category.Category
import com.example.financesapp.data.remote.models.transaction.Transaction
import com.example.financesapp.data.remote.models.transaction.TransactionRequest
import com.example.financesapp.data.remote.models.transaction.TransactionResponseDto
import com.example.financesapp.domain.repository.RemoteDataSourceRepository
import kotlinx.coroutines.delay

class RemoteDataSourceImpl(
    private val api: ApiService
) : RemoteDataSourceRepository {

    override suspend fun getAccount(): AccountDto {
        return api.getAccounts().first()
    }

    override suspend fun getAccountById(id: Int): AccountResponse {
        return api.getAccountById(id)
    }

    override suspend fun createAccount(request: AccountRequest): AccountDto {
        return api.createAccount(request)
    }

    override suspend fun updateAccount(id: Int, request: AccountRequest): AccountDto {
        return api.updateAccountById(id, request)
    }

    override suspend fun deleteAccount(id: Int) {
        return api.deleteAccountById(id)
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponse {
        return api.getAccountHistory(id)
    }

    override suspend fun getCategories(): List<Category> {
        return api.getCategories()
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
        return api.getCategoriesByType(isIncome)
    }

    override suspend fun createTransaction(request: TransactionRequest): Transaction {
        return api.createTransaction(request)
    }

    override suspend fun getTransactionById(id: Int): TransactionResponseDto {
        return api.getTransactionById(id)
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequest
    ): TransactionResponseDto {
        return api.updateTransaction(id, request)
    }

    override suspend fun deleteTransaction(id: Int) {
        return api.deleteTransaction(id)
    }

    override suspend fun getTransactionsByPeriod(
        startDate: String?,
        endDate: String?
    ): List<TransactionResponseDto> {
        val maxRetries = 3
        val retryDelay = 2000L
        var currentRetry = 0
        var lastException: Exception? = null

        while (currentRetry < maxRetries) {
            Log.d("testLog", "Domain retry --- $currentRetry")
            try {
                return api.getTransactionsByPeriod(
                    accountId = getAccount().id,
                    startDate = startDate,
                    endDate = endDate
                )
            } catch (e: Exception) {
                lastException = e
                currentRetry++
//                if (e !is IOException && e !is HttpException) {
//                    break
//                }
//
//                if (e is HttpException && e.code() != 500) {
//                    break
//                }

//                if (currentRetry < maxRetries) {
                delay(retryDelay)
//                }
            }
        }

        // Если дошли сюда, значит все попытки исчерпаны
        throw lastException ?: Exception("Unknown error occurred")
//        return api.getTransactionsByPeriod(
//            accountId = getAccount().id,
//            startDate = startDate,
//            endDate = endDate
//        )
    }
}
//
//// Проверка доступности сети
//private fun isNetworkAvailable(): Boolean {
//    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    val networkInfo = connectivityManager.activeNetworkInfo
//    return networkInfo != null && networkInfo.isConnected
//}
//
//// Кастомное исключение для отсутствия интернета
//class NoInternetException(message: String) : Exception(message)