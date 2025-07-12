package com.example.categories.di

import androidx.lifecycle.ViewModelProvider
import com.example.network.di.NetworkApi
import dagger.Component

@CategoriesScope
@Component(
    modules = [CategoriesModule::class],
    dependencies = [NetworkApi::class]
)
internal interface CategoriesComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): CategoriesComponent
    }
}
