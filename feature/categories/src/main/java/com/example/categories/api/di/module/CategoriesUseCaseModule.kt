//package com.example.categories.api.di.module
//
//import com.example.categories.api.di.scope.CategoriesScope
//import com.example.categories.impl.domain.repository.CategoriesRepository
//import com.example.categories.impl.domain.usecase.impl.GetCategoriesUseCaseImpl
//import com.example.categories.impl.domain.usecase.interfaces.GetCategoriesUseCase
//import dagger.Module
//import dagger.Provides
//
//@Module
//object CategoriesUseCaseModule {
//
//    @CategoriesScope
//    @Provides
//    fun provideGetCategoriesUseCase(
//        categoriesRepository: CategoriesRepository
//    ): GetCategoriesUseCase = GetCategoriesUseCaseImpl(categoriesRepository)
//}
