package com.example.financesapp.data.remote.api

import com.example.financesapp.data.remote.models.account.AccountDto
import com.example.financesapp.data.remote.models.account.AccountRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountsApi {

    @GET("accounts")
    suspend fun getAccounts(): List<AccountDto>

    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Path("id") id: Int,
        @Body accountRequest: AccountRequestDto
    ): AccountDto

    @GET("accounts/{is}")
    suspend fun getAccountById(
        @Path("id") id: Int
    ): AccountDto
}
