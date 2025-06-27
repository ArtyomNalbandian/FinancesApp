package com.example.financesapp

class FinancesApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }
}