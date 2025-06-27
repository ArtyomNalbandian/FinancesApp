package com.example.financesapp.di.module

import com.example.financesapp.data.remote.repository.AccountRepositoryImpl
import com.example.financesapp.data.remote.repository.TransactionRepositoryImpl
import com.example.financesapp.domain.repositories.AccountRepository
import com.example.financesapp.domain.repositories.TransactionRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindAccountRepository(repository: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    fun bindTransactionRepository(repository: TransactionRepositoryImpl): TransactionRepository
}
