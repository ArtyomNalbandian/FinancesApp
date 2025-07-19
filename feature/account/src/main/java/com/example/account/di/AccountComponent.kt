package com.example.account.di

import androidx.lifecycle.ViewModelProvider
import com.example.database.di.DatabaseApi
import com.example.network.di.NetworkApi
import dagger.Component

@AccountScope
@Component(
    modules = [AccountModule::class],
    dependencies = [NetworkApi::class, DatabaseApi::class]
)
interface AccountComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(
            networkApi: NetworkApi,
            databaseApi: DatabaseApi
        ): AccountComponent
    }
}
