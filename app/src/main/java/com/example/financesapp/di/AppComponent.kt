package com.example.financesapp.di

//import com.example.financesapp.di.module.AppModule
import com.example.network.di.NetworkComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NetworkComponent::class]
)
interface AppComponent
