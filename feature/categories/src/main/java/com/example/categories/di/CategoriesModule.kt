package com.example.categories.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.categories.data.repository.CategoriesRepositoryImpl
import com.example.categories.domain.repository.CategoriesRepository
import com.example.categories.domain.usecase.impl.GetCategoriesUseCaseImpl
import com.example.categories.domain.usecase.interfaces.GetCategoriesUseCase
import com.example.categories.presentation.CategoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class CategoriesModule {
    @Binds
    @CategoriesScope
    abstract fun bindCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    @CategoriesScope
    abstract fun bindGetCategoriesUseCase(impl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel

    @Binds
    @CategoriesScope
    abstract fun bindViewModelFactory(factory: CategoriesViewModelFactory): ViewModelProvider.Factory
}
