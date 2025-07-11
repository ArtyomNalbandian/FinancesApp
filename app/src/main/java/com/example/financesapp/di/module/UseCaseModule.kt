package com.example.financesapp.di.module

import com.example.account.domain.usecase.impl.GetAccountUseCaseImpl
import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.financesapp.domain.usecases.impl.GetExpensesUseCaseImpl
import com.example.financesapp.domain.usecases.impl.GetIncomesUseCaseImpl
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
        useCase: GetAccountUseCaseImpl
    ): GetAccountUseCase

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

//    @Binds
//    @Singleton
//    abstract fun bindUpdateAccountUseCase(
//        useCase: UpdateAccountUseCaseImpl
//    ): UpdateAccountUseCase

//    @Binds
//    @Singleton
//    abstract fun bindGetAccountByIdUseCase(
//        useCase: GetAccountByIdUseCaseImpl
//    ): GetAccountByIdUseCase
}
