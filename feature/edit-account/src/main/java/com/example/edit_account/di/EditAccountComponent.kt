package com.example.edit_account.di

import androidx.lifecycle.ViewModelProvider
import com.example.database.di.DatabaseApi
import com.example.network.di.NetworkApi
import dagger.Component

@EditAccountScope
@Component(
    modules = [EditAccountModule::class],
    dependencies = [NetworkApi::class, DatabaseApi::class]
)
internal interface EditAccountComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(
            networkApi: NetworkApi,
            databaseApi: DatabaseApi
        ): EditAccountComponent
    }
}
