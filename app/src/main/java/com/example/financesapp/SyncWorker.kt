package com.example.financesapp

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.database.di.DaggerDatabaseComponent
import com.example.database.sync.SyncManager
import com.example.network.di.DaggerNetworkComponent

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", "WorkManager запущен")
        val networkComponent = DaggerNetworkComponent.create()
        val databaseComponent = DaggerDatabaseComponent.builder()
            .context(applicationContext)
            .build()
        return try {
            SyncManager.syncAll(
                context = applicationContext,
                accountDao = databaseComponent.accountDao(),
                transactionDao = databaseComponent.transactionDao(),
                accountsApi = networkComponent.accountsApi(),
                transactionApi = networkComponent.transactionApi()
            )
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
