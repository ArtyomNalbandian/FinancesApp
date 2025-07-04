package com.example.financesapp.di.module

import com.example.financesapp.domain.usecases.impl.GetAccountByIdUseCaseImpl
import com.example.financesapp.domain.usecases.impl.GetAccountsUseCaseImpl
import com.example.financesapp.domain.usecases.impl.GetCategoriesUseCaseImpl
import com.example.financesapp.domain.usecases.impl.GetExpensesUseCaseImpl
import com.example.financesapp.domain.usecases.impl.GetIncomesUseCaseImpl
import com.example.financesapp.domain.usecases.interfaces.GetAccountByIdUseCase
import com.example.financesapp.domain.usecases.interfaces.GetAccountsUseCase
import com.example.financesapp.domain.usecases.interfaces.GetCategoriesUseCase
import com.example.financesapp.domain.usecases.interfaces.GetExpensesUseCase
import com.example.financesapp.domain.usecases.interfaces.GetIncomesUseCase
import com.example.financesapp.domain.usecases.interfaces.UpdateAccountUseCase
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

//    @Binds
//    @Singleton
//    abstract fun bindUpdateAccountUseCase(
//        useCase: UpdateAccountUseCaseImpl
//    ): UpdateAccountUseCase

    @Binds
    @Singleton
    abstract fun bindGetAccountByIdUseCase(
        useCase: GetAccountByIdUseCaseImpl
    ): GetAccountByIdUseCase

    @Binds
    @Singleton
    abstract fun bindGetCategoriesUseCase(
        useCase: GetCategoriesUseCaseImpl
    ): GetCategoriesUseCase
}
