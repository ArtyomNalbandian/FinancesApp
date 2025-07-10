package com.example.categories.api.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.categories.api.di.scope.CategoriesScope
import com.example.categories.impl.data.repository.CategoriesRepositoryImpl
import com.example.categories.impl.domain.repository.CategoriesRepository
import com.example.categories.impl.domain.usecase.impl.GetCategoriesUseCaseImpl
import com.example.categories.impl.domain.usecase.interfaces.GetCategoriesUseCase
import com.example.categories.impl.presentation.CategoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

//@Module
//interface CategoriesModule {
//
//    @Binds
//    @CategoriesScope
//    fun bindCategoriesRepository(repository: CategoriesRepositoryImpl): CategoriesRepository
//
//    @Binds
//    @CategoriesScope
//    fun bindGetCategoriesUseCase(repository: GetCategoriesUseCaseImpl): GetCategoriesUseCase
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CategoriesViewModel::class)
//    @CategoriesScope
//    fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel
//}

// feature/categories/di/CategoriesModule.kt
@Module
abstract class CategoriesModule {
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
