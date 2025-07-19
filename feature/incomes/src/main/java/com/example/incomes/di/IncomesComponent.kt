package com.example.incomes.di

import androidx.lifecycle.ViewModelProvider
import com.example.database.di.DatabaseApi
import com.example.network.di.NetworkApi
import dagger.Component

@IncomesScope
@Component(
    modules = [IncomesModule::class],
    dependencies = [NetworkApi::class, DatabaseApi::class]
)
internal interface IncomesComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(
            networkApi: NetworkApi,
            databaseApi: DatabaseApi
        ): IncomesComponent
    }
}
