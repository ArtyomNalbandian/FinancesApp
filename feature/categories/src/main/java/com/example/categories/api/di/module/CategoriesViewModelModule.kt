//package com.example.categories.api.di.module
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.categories.api.di.scope.CategoriesScope
//import com.example.categories.impl.presentation.CategoriesViewModel
//import dagger.Binds
//import dagger.MapKey
//import dagger.Module
//import dagger.multibindings.IntoMap
//import javax.inject.Inject
//import javax.inject.Provider
//import javax.inject.Singleton
//import kotlin.reflect.KClass
//
//@Module
//interface CategoriesViewModelModule {
//
//    @Binds
//    fun bindCategoriesViewModelFactory(factory: CategoriesViewModelFactory): ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CategoriesViewModel::class)
//    fun bindCategoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel
//}
//
//@Target(
//    AnnotationTarget.FUNCTION,
//    AnnotationTarget.PROPERTY_GETTER,
//    AnnotationTarget.PROPERTY_SETTER
//)
//@Retention(AnnotationRetention.RUNTIME)
//@MapKey
//annotation class ViewModelKey(val value: KClass<out ViewModel>)
//
//@CategoriesScope
//class CategoriesViewModelFactory @Inject constructor(
//    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
//) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        val viewModelProvider = viewModels[modelClass]
//            ?: throw IllegalArgumentException("Unknown model class $modelClass")
//
//        try {
//            return viewModelProvider.get() as T
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
//    }
//}