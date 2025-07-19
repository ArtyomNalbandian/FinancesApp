package com.example.edit_account.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.account.data.repository.AccountRepositoryImpl
import com.example.account.domain.repository.AccountRepository
import com.example.account.domain.usecase.impl.GetAccountUseCaseImpl
import com.example.account.domain.usecase.impl.UpdateAccountUseCaseImpl
import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.account.domain.usecase.interfaces.UpdateAccountUseCase
import com.example.edit_account.presentation.EditAccountViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class EditAccountModule {

    @Binds
    @EditAccountScope
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @EditAccountScope
    abstract fun bindGetAccountUseCase(impl: GetAccountUseCaseImpl): GetAccountUseCase

    @Binds
    @EditAccountScope
    abstract fun bindUpdateAccountUseCase(impl: UpdateAccountUseCaseImpl): UpdateAccountUseCase

    @Binds
    @IntoMap
    @ViewModelKey(EditAccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: EditAccountViewModel): ViewModel

    @Binds
    @EditAccountScope
    abstract fun bindViewModelFactory(factory: EditAccountViewModelFactory): ViewModelProvider.Factory
}
