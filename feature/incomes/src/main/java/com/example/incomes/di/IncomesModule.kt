package com.example.incomes.di

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
import com.example.incomes.data.repository.IncomesRepositoryImpl
import com.example.incomes.domain.repository.IncomesRepository
import com.example.incomes.domain.usecase.impl.CreateIncomeUseCaseImpl
import com.example.incomes.domain.usecase.impl.DeleteIncomeUseCaseImpl
import com.example.incomes.domain.usecase.impl.GetIncomeByIdUseCaseImpl
import com.example.incomes.domain.usecase.impl.GetIncomesUseCaseImpl
import com.example.incomes.domain.usecase.impl.UpdateIncomeUseCaseImpl
import com.example.incomes.domain.usecase.interfaces.CreateIncomeUseCase
import com.example.incomes.domain.usecase.interfaces.DeleteIncomeUseCase
import com.example.incomes.domain.usecase.interfaces.GetIncomeByIdUseCase
import com.example.incomes.domain.usecase.interfaces.GetIncomesUseCase
import com.example.incomes.domain.usecase.interfaces.UpdateIncomeUseCase
import com.example.incomes.presentation.incomes.IncomesViewModel
import com.example.incomes.presentation.incomes_add.IncomesAddViewModel
import com.example.incomes.presentation.incomes_analysis.IncomesAnalysisViewModel
import com.example.incomes.presentation.incomes_edit.IncomesEditViewModel
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
    abstract fun bindCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    @IncomesScope
    abstract fun bindGetIncomesUseCase(impl: GetIncomesUseCaseImpl): GetIncomesUseCase

    @Binds
    @IncomesScope
    abstract fun bindGetIncomeByIdUseCase(impl: GetIncomeByIdUseCaseImpl): GetIncomeByIdUseCase

    @Binds
    @IncomesScope
    abstract fun bindCreateIncomeUseCase(impl: CreateIncomeUseCaseImpl): CreateIncomeUseCase

    @Binds
    @IncomesScope
    abstract fun bindDeleteIncomeUseCase(impl: DeleteIncomeUseCaseImpl): DeleteIncomeUseCase

    @Binds
    @IncomesScope
    abstract fun bindUpdateIncomeUseCase(impl: UpdateIncomeUseCaseImpl): UpdateIncomeUseCase

    @Binds
    @IncomesScope
    abstract fun bindGetAccountUseCase(impl: GetAccountUseCaseImpl): GetAccountUseCase

    @Binds
    @IncomesScope
    abstract fun bindGetCategoriesUseCase(impl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Binds
    @IntoMap
    @ViewModelKey(IncomesViewModel::class)
    abstract fun bindIncomesViewModel(viewModel: IncomesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesHistoryViewModel::class)
    abstract fun bindIncomeHistoryViewModel(viewModel: IncomesHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesAnalysisViewModel::class)
    abstract fun bindIncomesAnalysisViewModel(viewModel: IncomesAnalysisViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesAddViewModel::class)
    abstract fun bindIncomeAddViewModel(viewModel: IncomesAddViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesEditViewModel::class)
    abstract fun bindIncomeEditViewModel(viewModel: IncomesEditViewModel): ViewModel

    @Binds
    @IncomesScope
    abstract fun bindViewModelFactory(factory: IncomesViewModelFactory): ViewModelProvider.Factory
}
