package com.example.financesapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.common.util.SyncPreferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class FinancesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(SyncObserver(this))
        // Подписка на изменения частоты синхронизации
        CoroutineScope(Dispatchers.Default).launch {
            SyncPreferencesDataStore.syncFrequencyFlow(this@FinancesApplication)
                .collect { hours ->
                    val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(hours.toLong(), TimeUnit.HOURS)
                        .build()
                    WorkManager.getInstance(this@FinancesApplication).enqueueUniquePeriodicWork(
                        "sync_work",
                        ExistingPeriodicWorkPolicy.UPDATE,
                        syncRequest
                    )
                }
        }
    }
}
