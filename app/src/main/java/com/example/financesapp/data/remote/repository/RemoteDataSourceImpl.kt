//package com.example.financesapp.data.remote.repository
//
//import com.example.financesapp.data.remote.api.ApiService
//import com.example.financesapp.data.remote.models.account.AccountDto
//import com.example.financesapp.data.remote.models.account.AccountHistoryResponseDto
//import com.example.financesapp.data.remote.models.account.AccountRequestDto
//import com.example.financesapp.data.remote.models.account.AccountResponseDto
//import com.example.financesapp.data.remote.models.category.Category
//import com.example.financesapp.data.remote.models.transaction.Transaction
//import com.example.financesapp.data.remote.models.transaction.TransactionRequest
//import com.example.financesapp.data.remote.models.transaction.TransactionResponseDto
//import com.example.financesapp.domain.repositories.RemoteDataSourceRepository
//import com.example.financesapp.utils.retryRequest
//import retrofit2.HttpException
//import java.io.IOException
//import java.net.UnknownHostException
//
//class RemoteDataSourceImpl(
//    private val api: ApiService
//) : RemoteDataSourceRepository {
//
//    override suspend fun getAccount(): AccountDto {
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
//            api.getAccounts().first()
//        }
//    }
//
//    override suspend fun getAccountById(id: Int): AccountResponseDto {
//        return api.getAccountById(id)
//    }
//
//    override suspend fun createAccount(request: AccountRequestDto): AccountDto {
//        return api.createAccount(request)
//    }
//
//    override suspend fun updateAccount(id: Int, request: AccountRequestDto): AccountDto {
//        return api.updateAccountById(id, request)
//    }
//
//    override suspend fun deleteAccount(id: Int) {
//        return api.deleteAccountById(id)
//    }
//
//    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseDto {
//        return api.getAccountHistory(id)
//    }
//
//    override suspend fun getCategories(): List<Category> {
//        return api.getCategories()
//    }
//
//    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
//        return api.getCategoriesByType(isIncome)
//    }
//
//    override suspend fun createTransaction(request: TransactionRequest): Transaction {
//        return api.createTransaction(request)
//    }
//
//    override suspend fun getTransactionById(id: Int): TransactionResponseDto {
//        return api.getTransactionById(id)
//    }
//
//    override suspend fun updateTransaction(
//        id: Int,
//        request: TransactionRequest
//    ): TransactionResponseDto {
//        return api.updateTransaction(id, request)
//    }
//
//    override suspend fun deleteTransaction(id: Int) {
//        return api.deleteTransaction(id)
//    }
//
//    override suspend fun getTransactionsByPeriod(
//        startDate: String?,
//        endDate: String?
//    ): List<TransactionResponseDto> {
//        return retryRequest(
//            shouldRetry = { throwable ->
//                when (throwable) {
//                    is IOException -> true
//                    is HttpException -> throwable.code() in 500..599
//                    else -> false
//                }
//            }
//        ) {
//            api.getTransactionsByPeriod(
//                accountId = getAccount().id,
//                startDate = startDate,
//                endDate = endDate
//            )
//        }
//    }
//}
