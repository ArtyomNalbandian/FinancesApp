package com.example.financesapp.data.remote.repository

import com.example.financesapp.data.remote.api.AccountApi
import com.example.financesapp.data.remote.models.Account
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AccountRepositoryImpl : AccountRepository {
    companion object {
        private const val BASE_URL = "https://shmr-finance.ru/api/v1/"
        private const val AUTH_TOKEN = "CCMQdMKNQDpmGnrGWVPY6Kw9"
    }

    private val authInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $AUTH_TOKEN")
                .addHeader("accept", "application/json")
                .build()
            return chain.proceed(request)
        }
    }

    private val api: AccountApi by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AccountApi::class.java)
    }

    override suspend fun getAccounts(): List<Account> {
        return api.getAccounts()
    }
}