package com.example.edit_account.di

import androidx.lifecycle.ViewModelProvider
import com.example.network.di.NetworkApi
import dagger.Component

@EditAccountScope
@Component(
    modules = [EditAccountModule::class],
    dependencies = [NetworkApi::class]
)
internal interface EditAccountComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): EditAccountComponent
    }
}
