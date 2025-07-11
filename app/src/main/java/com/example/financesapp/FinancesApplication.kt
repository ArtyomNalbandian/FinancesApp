package com.example.financesapp

import android.app.Application
import android.content.Context
import com.example.financesapp.di.AppComponent
import com.example.financesapp.di.DaggerAppComponent
import com.example.network.di.DaggerNetworkComponent

class FinancesApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        val networkComponent = DaggerNetworkComponent.create()

        appComponent = DaggerAppComponent
            .factory()
            .create(
                context = this,
                networkComponent = networkComponent
            )
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is FinancesApplication -> appComponent
        else -> applicationContext.appComponent
    }
