//package com.example.categories.api.di.component
//
//import androidx.lifecycle.ViewModelProvider
//import com.example.categories.api.di.module.CategoriesRepositoryModule
//import com.example.categories.api.di.module.CategoriesUseCaseModule
//import com.example.categories.api.di.module.CategoriesViewModelModule
//import com.example.categories.api.di.scope.CategoriesScope
//import dagger.Subcomponent
//
//@CategoriesScope
//@Subcomponent(
//    modules = [
//        CategoriesViewModelModule::class,
//        CategoriesUseCaseModule::class,
//        CategoriesRepositoryModule::class,
//    ]
//)
//interface CategoriesComponent {
//
//    fun categoriesViewModelFactory(): ViewModelProvider.Factory
//
//    @Subcomponent.Factory
//    interface Factory {
//        fun create(): CategoriesComponent
//    }
//}
