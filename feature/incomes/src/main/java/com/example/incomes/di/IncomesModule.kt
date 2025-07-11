package com.example.incomes.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.account.data.repository.AccountRepositoryImpl
import com.example.account.domain.repository.AccountRepository
import com.example.account.domain.usecase.impl.GetAccountUseCaseImpl
import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.incomes.data.repository.IncomesRepositoryImpl
import com.example.incomes.domain.repository.IncomesRepository
import com.example.incomes.domain.usecase.impl.GetIncomesUseCaseImpl
import com.example.incomes.domain.usecase.interfaces.GetIncomesUseCase
import com.example.incomes.presentation.incomes.IncomesViewModel
import com.example.incomes.presentation.incomes_history.IncomesHistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class IncomesModule {
    @Binds
    @IncomesScope
    abstract fun bindIncomesRepository(impl: IncomesRepositoryImpl): IncomesRepository

    @Binds
    @IncomesScope
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @IncomesScope
    abstract fun bindGetIncomesUseCase(impl: GetIncomesUseCaseImpl): GetIncomesUseCase

    @Binds
    @IncomesScope
    abstract fun bindGetAccountUseCase(impl: GetAccountUseCaseImpl): GetAccountUseCase

    @Binds
    @IntoMap
    @ViewModelKey(IncomesViewModel::class)
    abstract fun bindIncomesViewModel(viewModel: IncomesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesHistoryViewModel::class)
    abstract fun bindExpensesHistoryViewModel(viewModel: IncomesHistoryViewModel): ViewModel

    @Binds
    @IncomesScope
    abstract fun bindViewModelFactory(factory: IncomesViewModelFactory): ViewModelProvider.Factory
}
