package com.example.categories.di

import androidx.lifecycle.ViewModelProvider
import com.example.database.di.DatabaseApi
import com.example.network.di.NetworkApi
import dagger.Component

@CategoriesScope
@Component(
    modules = [CategoriesModule::class],
    dependencies = [NetworkApi::class, DatabaseApi::class]
)
internal interface CategoriesComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(
            networkApi: NetworkApi,
            databaseApi: DatabaseApi
        ): CategoriesComponent
    }
}
