package com.example.financesapp

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.common.util.NetworkMonitor
import com.example.database.di.DaggerDatabaseComponent
import com.example.database.sync.SyncManager
import com.example.network.di.DaggerNetworkComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SyncObserver(
    private val application: Application
) : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        val networkComponent = DaggerNetworkComponent.create()
        val databaseComponent = DaggerDatabaseComponent.builder()
            .context(application.applicationContext)
            .build()
        CoroutineScope(Dispatchers.IO).launch {
            NetworkMonitor.observe(application).collectLatest { isConnected ->
                if (isConnected) {
                    SyncManager.syncAll(
                        context = application,
                        accountDao = databaseComponent.accountDao(),
                        categoryDao = databaseComponent.categoryDao(),
                        transactionDao = databaseComponent.transactionDao(),
                        accountsApi = networkComponent.accountsApi(),
                        categoriesApi = networkComponent.categoriesApi(),
                        transactionApi = networkComponent.transactionApi()
                    )
                }
            }
        }
    }
}
