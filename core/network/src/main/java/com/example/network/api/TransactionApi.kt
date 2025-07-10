package com.example.network.api

import com.example.network.dto.transaction.TransactionResponseDto
import com.example.network.util.ApiUrls.TRANSACTIONS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApi {

    @GET("$TRANSACTIONS/account/{accountId}/period")
    suspend fun getTransactionsByPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<TransactionResponseDto>
}
