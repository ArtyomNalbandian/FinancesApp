package com.example.incomes.di

import androidx.lifecycle.ViewModelProvider
import com.example.network.di.NetworkApi
import dagger.Component

@IncomesScope
@Component(
    modules = [IncomesModule::class],
    dependencies = [NetworkApi::class]
)
internal interface IncomesComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): IncomesComponent
    }
}
