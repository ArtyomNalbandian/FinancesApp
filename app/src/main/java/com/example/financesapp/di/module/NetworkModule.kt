//package com.example.financesapp.di.module
//
//import com.example.financesapp.BuildConfig
//import com.example.financesapp.data.remote.api.AccountsApi
//import com.example.financesapp.data.remote.api.CategoriesApi
//import com.example.financesapp.data.remote.api.TransactionApi
//import dagger.Module
//import dagger.Provides
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//class NetworkModule {
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val request = chain.request()
//                    .newBuilder()
//                    .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("https://shmr-finance.ru/api/v1/")
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideAccountsApi(retrofit: Retrofit): AccountsApi {
//        return retrofit.create(AccountsApi::class.java)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideTransactionApi(retrofit: Retrofit): TransactionApi {
//        return retrofit.create(TransactionApi::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi {
//        return retrofit.create(CategoriesApi::class.java)
//    }
//}
