package com.example.financesapp.data.remote.api

import com.example.financesapp.data.remote.models.Account
import retrofit2.http.GET
import retrofit2.http.Header

interface AccountApi {

    @GET("accounts")
    suspend fun getAccounts(
//        @Header("Authrization") token: String = "Bearer CCMQdMKNQDpmGnrGWVPY6Kw9",
//        @Header("accept") accept: String = "application/json"
    ): List<Account>

}