package com.example.financesapp.di

import android.content.Context
import com.example.financesapp.MainActivity
import com.example.financesapp.di.module.AppModule
import com.example.financesapp.di.module.RepositoryModule
import com.example.financesapp.di.module.UseCaseModule
import com.example.financesapp.di.module.ViewModelModule
import com.example.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
