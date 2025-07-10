package com.example.categories.api.di

import androidx.lifecycle.ViewModelProvider
import com.example.categories.api.di.scope.CategoriesScope
import com.example.network.di.NetworkApi
import dagger.Component

//@CategoriesScope
//@Subcomponent(
////    dependencies = [CategoriesDependencies::class],
//    modules = [CategoriesModule::class]
//)
//interface CategoriesComponent {
//
//    fun inject(viewModel: CategoriesViewModel)
//
//    @Component.Factory
//    interface Factory {
//        fun create(): CategoriesComponent
//    }
//}

//@CategoriesScope
//@Component(
//    modules = [
//        CategoriesModule::class,
//        CategoriesViewModelModule::class
//    ],
//    dependencies = [CategoriesDependencies::class]
//)
//interface CategoriesComponent {
//    fun viewModelsMap(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
//
//    @Component.Factory
//    interface Factory {
//        fun create(dependencies: CategoriesDependencies): CategoriesComponent
//    }
//}

//@Scope
//@Retention(AnnotationRetention.RUNTIME)
//annotation class CategoriesScope

@CategoriesScope
@Component(
    modules = [CategoriesModule::class],
    dependencies = [NetworkApi::class]
)
interface CategoriesComponent {
    //    fun inject(viewModel: CategoriesViewModel)
    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): CategoriesComponent
    }
}
