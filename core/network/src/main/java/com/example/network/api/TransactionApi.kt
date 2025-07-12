package com.example.network.api

import com.example.network.dto.transaction.TransactionDto
import com.example.network.dto.transaction.TransactionRequestDto
import com.example.network.dto.transaction.TransactionResponseDto
import com.example.network.util.ApiUrls.TRANSACTIONS
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApi {

    @GET("$TRANSACTIONS/account/{accountId}/period")
    suspend fun getTransactionsByPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<TransactionResponseDto>

    @GET("$TRANSACTIONS/{id}")
    suspend fun getTransactionById(@Path("id") id: Int): TransactionResponseDto

    @PUT("$TRANSACTIONS/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body request: TransactionRequestDto
    ): TransactionResponseDto

    @DELETE("$TRANSACTIONS/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int)

    @POST("transactions")
    suspend fun createTransaction(@Body request: TransactionRequestDto): TransactionDto
}
