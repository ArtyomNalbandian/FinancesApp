package com.example.financesapp.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.financesapp.MainActivity
import com.example.financesapp.di.module.AppModule
import com.example.financesapp.di.module.ApplicationContext
import com.example.financesapp.di.module.RepositoryModule
import com.example.financesapp.di.module.UseCaseModule
import com.example.financesapp.di.module.ViewModelModule
import com.example.network.di.NetworkComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NetworkComponent::class],
    modules = [
        AppModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)

    fun viewModelFactory(): ViewModelProvider.Factory

//    fun categoriesComponentFactory(): CategoriesComponent.Factory

//    val networkComponent: NetworkComponent

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @ApplicationContext context: Context,
            networkComponent: NetworkComponent
        ): AppComponent
    }
}

//@Singleton
//@Component(
//    modules = [
//        AppModule::class,
////        NetworkModule::class,
//        RepositoryModule::class,
//        ViewModelModule::class,
//        UseCaseModule::class,
//        ViewModelFactoryModule::class
//    ],
//    dependencies = [NetworkComponent::class]
//)
//interface AppComponent: CategoriesDependencies {
//
//    fun inject(activity: MainActivity)
//
//    fun categoriesComponentFactory(): CategoriesComponent.Factory
//
//    fun appViewModels(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
//
//    @Component.Factory
//    interface Factory {
//        fun create(
//            @BindsInstance @ApplicationContext
//            context: Context,
//            networkComponent: NetworkComponent): AppComponent
//    }
//
//    override fun categoriesApi(): CategoriesApi
//}

//@Module
//object ViewModelFactoryModule {
//    @Provides
//    @Singleton
//    fun provideViewModelFactory(
//        appViewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
//        categoriesComponentFactory: CategoriesComponent.Factory,
//        appComponent: AppComponent
//    ): ViewModelProvider.Factory {
//        val categoriesComponent = categoriesComponentFactory.create(appComponent)
//
//        val combinedMap = mutableMapOf<Class<out ViewModel>, Provider<ViewModel>>()
//        combinedMap.putAll(appViewModels)
//        combinedMap.putAll(categoriesComponent.viewModelsMap())
//
//        return ViewModelFactory(combinedMap)
//    }
//
//    @Provides
//    fun provideAppViewModels(component: AppComponent): Map<Class<out ViewModel>, Provider<ViewModel>> {
//        return component.appViewModels()
//    }
//}

//@Module
//object ViewModelFactoryModule {
//    @Provides
//    @Singleton
//    fun provideViewModelFactory(
//        appComponent: AppComponent,
//        categoriesComponentFactory: CategoriesComponent.Factory
//    ): ViewModelProvider.Factory {
//        val categoriesComponent = categoriesComponentFactory.create(appComponent)
//
//        val viewModelsMap = mutableMapOf<Class<out ViewModel>, Provider<ViewModel>>()
//        viewModelsMap.putAll(appComponent.appViewModels())
//        viewModelsMap.putAll(categoriesComponent.viewModelsMap())
//
//        return ViewModelFactory(viewModelsMap)
//    }
//}
