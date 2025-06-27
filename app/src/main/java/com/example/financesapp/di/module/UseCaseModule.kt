package com.example.financesapp.di.module

import com.example.financesapp.domain.usecases.impl.GetAccountsUseCaseImpl
import com.example.financesapp.domain.usecases.impl.GetExpensesUseCaseImpl
import com.example.financesapp.domain.usecases.impl.GetIncomesUseCaseImpl
import com.example.financesapp.domain.usecases.interfaces.GetAccountsUseCase
import com.example.financesapp.domain.usecases.interfaces.GetExpensesUseCase
import com.example.financesapp.domain.usecases.interfaces.GetIncomesUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindGetAccountsUseCase(
        useCase: GetAccountsUseCaseImpl
    ): GetAccountsUseCase

    @Binds
    @Singleton
    abstract fun bindGetExpensesUseCase(
        useCase: GetExpensesUseCaseImpl
    ): GetExpensesUseCase

    @Binds
    @Singleton
    abstract fun bindGetIncomesUseCase(
        useCase: GetIncomesUseCaseImpl
    ): GetIncomesUseCase
}
