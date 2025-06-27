package com.example.financesapp.data.remote.api

import com.example.financesapp.data.remote.models.account.AccountDto
import retrofit2.http.GET

interface AccountsApi {

    @GET("accounts")
    suspend fun getAccounts(): List<AccountDto>
}
