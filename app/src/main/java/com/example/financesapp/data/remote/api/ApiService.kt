package com.example.financesapp.data.remote.api

import com.example.financesapp.data.remote.models.account.AccountHistoryResponseDto
import com.example.financesapp.data.remote.models.account.AccountDto
import com.example.financesapp.data.remote.models.account.AccountRequestDto
import com.example.financesapp.data.remote.models.account.AccountResponseDto
import com.example.financesapp.data.remote.models.category.Category
import com.example.financesapp.data.remote.models.transaction.Transaction
import com.example.financesapp.data.remote.models.transaction.TransactionRequest
import com.example.financesapp.data.remote.models.transaction.TransactionResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("accounts")
    suspend fun getAccounts(): List<AccountDto>

    @POST("accounts")
    suspend fun createAccount(@Body request: AccountRequestDto): AccountDto

    @GET("account/{id}")
    suspend fun getAccountById(@Path("id") id: Int): AccountResponseDto

    @PUT("accounts/{id}")
    suspend fun updateAccountById(
        @Path("id") id: Int,
        @Body request: AccountRequestDto
    ): AccountDto

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(@Path("id") id: Int)

    @GET("accounts/{id}/history")
    suspend fun getAccountHistory(@Path("id") id: Int): AccountHistoryResponseDto

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): List<Category>

    @POST("transactions")
    suspend fun createTransaction(@Body request: TransactionRequest): Transaction

    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") id: Int): TransactionResponseDto

    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body request: TransactionRequest
    ): TransactionResponseDto

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int): Unit

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<TransactionResponseDto>

}