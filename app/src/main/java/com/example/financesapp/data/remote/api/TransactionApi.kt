package com.example.financesapp.data.remote.api

import com.example.financesapp.data.remote.models.transaction.TransactionResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApi {

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<TransactionResponseDto>

}
