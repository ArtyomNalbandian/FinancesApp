package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.api.AccountsApi
import com.example.network.api.CategoriesApi
import com.example.network.api.TransactionApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://shmr-finance.ru/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAccountsApi(retrofit: Retrofit): AccountsApi {
        return retrofit.create(AccountsApi::class.java)
    }


    @Provides
    fun provideTransactionApi(retrofit: Retrofit): TransactionApi {
        return retrofit.create(TransactionApi::class.java)
    }

    @Provides
    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi {
        return retrofit.create(CategoriesApi::class.java)
    }
}
