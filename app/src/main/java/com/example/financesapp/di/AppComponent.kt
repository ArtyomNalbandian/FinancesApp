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

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @ApplicationContext context: Context,
            networkComponent: NetworkComponent
        ): AppComponent
    }
}
