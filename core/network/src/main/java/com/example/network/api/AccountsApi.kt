package com.example.network.api

import com.example.network.dto.account.AccountDto
import com.example.network.dto.account.AccountRequestDto
import com.example.network.util.ApiUrls.ACCOUNTS
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountsApi {

    @GET(ACCOUNTS)
    suspend fun getAccounts(): List<AccountDto>

    @PUT("$ACCOUNTS/{id}")
    suspend fun updateAccount(
        @Path("id") id: Int,
        @Body accountRequest: AccountRequestDto
    ): AccountDto

    @GET("$ACCOUNTS/{id}")
    suspend fun getAccountById(
        @Path("id") id: Int
    ): AccountDto
}
