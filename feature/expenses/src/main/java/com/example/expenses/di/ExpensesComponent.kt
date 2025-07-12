package com.example.expenses.di

import androidx.lifecycle.ViewModelProvider
import com.example.network.di.NetworkApi
import dagger.Component

@ExpensesScope
@Component(
    modules = [ExpensesModule::class],
    dependencies = [NetworkApi::class]
)
internal interface ExpensesComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(networkApi: NetworkApi): ExpensesComponent
    }
}
