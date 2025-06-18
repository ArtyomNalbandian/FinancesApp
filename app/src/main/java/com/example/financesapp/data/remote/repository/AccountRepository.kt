package com.example.financesapp.data.remote.repository

import com.example.financesapp.data.remote.models.Account

interface AccountRepository {
    suspend fun getAccounts() : List<Account>
}