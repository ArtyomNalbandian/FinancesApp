package com.example.financesapp

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner

class FinancesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(SyncObserver(this))
    }
}
