package com.example.categories.api.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.categories.api.di.CategoriesComponent
//import com.example.categories.impl.presentation.CategoriesViewModel
//
////package com.example.categories.api.di
////
////import androidx.lifecycle.ViewModel
////import androidx.lifecycle.ViewModelProvider
////import com.example.categories.impl.presentation.CategoriesViewModel
////
////class CategoriesViewModelFactory(
////    private val dependencies: CategoriesDependencies
////) : ViewModelProvider.Factory {
////
////    @Suppress("UNCHECKED_CAST")
////    override fun <T : ViewModel> create(modelClass: Class<T>): T {
////        val component = CategoriesComponent.init(dependencies)
////        val viewModel = CategoriesViewModel(
////            component.getCategoriesUseCase()
////        )
////        component.inject(viewModel)
////        return viewModel as T
////    }
////}
//
//class CategoriesViewModelFactory(
//    private val categoriesComponent: CategoriesComponent
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
//            val viewModel = CategoriesViewModel(categoriesComponent.provideGetCategoriesUseCase())
//            categoriesComponent.inject(viewModel)
//            return viewModel as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

class CategoriesViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        return creator.get() as T
    }
}
