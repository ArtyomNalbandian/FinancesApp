//package com.example.categories.api.di
//
//import androidx.lifecycle.ViewModel
//import com.example.categories.impl.presentation.CategoriesViewModel
//import dagger.Binds
//import dagger.MapKey
//import dagger.Module
//import dagger.multibindings.IntoMap
//import kotlin.reflect.KClass
//
//@Module
//abstract class CategoriesViewModelModule {
//    @Binds
//    @IntoMap
//    @ViewModelKey(CategoriesViewModel::class)
//    abstract fun bindViewModel(viewModel: CategoriesViewModel): ViewModel
//}
//
//@Target(AnnotationTarget.FUNCTION)
//@Retention(AnnotationRetention.RUNTIME)
//@MapKey
//annotation class ViewModelKey(val value: KClass<out ViewModel>)