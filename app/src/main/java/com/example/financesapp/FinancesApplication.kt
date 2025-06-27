package com.example.financesapp

import android.app.Application
import android.content.Context
import com.example.financesapp.di.AppComponent
import com.example.financesapp.di.DaggerAppComponent

class FinancesApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is FinancesApplication -> appComponent
        else -> applicationContext.appComponent
    }
