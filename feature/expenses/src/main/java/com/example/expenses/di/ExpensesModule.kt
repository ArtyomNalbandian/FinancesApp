package com.example.expenses.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.account.data.repository.AccountRepositoryImpl
import com.example.account.domain.repository.AccountRepository
import com.example.account.domain.usecase.impl.GetAccountUseCaseImpl
import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.categories.data.repository.CategoriesRepositoryImpl
import com.example.categories.domain.repository.CategoriesRepository
import com.example.categories.domain.usecase.impl.GetCategoriesUseCaseImpl
import com.example.categories.domain.usecase.interfaces.GetCategoriesUseCase
import com.example.expenses.data.repository.ExpensesRepositoryImpl
import com.example.expenses.domain.repository.ExpensesRepository
import com.example.expenses.domain.usecase.impl.CreateExpenseUseCaseImpl
import com.example.expenses.domain.usecase.impl.GetExpensesUseCaseImpl
import com.example.expenses.domain.usecase.interfaces.CreateExpenseUseCase
import com.example.expenses.domain.usecase.interfaces.GetExpensesUseCase
import com.example.expenses.presentation.expenses.ExpensesViewModel
import com.example.expenses.presentation.expenses_add.ExpensesAddViewModel
import com.example.expenses.presentation.expenses_history.ExpensesHistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ExpensesModule {
    @Binds
    @ExpensesScope
    abstract fun bindExpensesRepository(impl: ExpensesRepositoryImpl): ExpensesRepository

    @Binds
    @ExpensesScope
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @ExpensesScope
    abstract fun bindCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    @ExpensesScope
    abstract fun bindGetExpensesUseCase(impl: GetExpensesUseCaseImpl): GetExpensesUseCase

    @Binds
    @ExpensesScope
    abstract fun bindCreateExpenseUseCase(impl: CreateExpenseUseCaseImpl): CreateExpenseUseCase

    @Binds
    @ExpensesScope
    abstract fun bindGetAccountUseCase(impl: GetAccountUseCaseImpl): GetAccountUseCase

    @Binds
    @ExpensesScope
    abstract fun bindGetCategoriesUseCase(impl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesViewModel::class)
    abstract fun bindExpensesViewModel(viewModel: ExpensesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesHistoryViewModel::class)
    abstract fun bindExpensesHistoryViewModel(viewModel: ExpensesHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesAddViewModel::class)
    abstract fun bindExpensesAddViewModel(viewModel: ExpensesAddViewModel): ViewModel

    @Binds
    @ExpensesScope
    abstract fun bindViewModelFactory(factory: ExpensesViewModelFactory): ViewModelProvider.Factory
}
