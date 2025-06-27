package com.example.financesapp.domain.repositories

import com.example.financesapp.domain.models.account.Account

interface AccountRepository {

    suspend fun getAccount(): Account
}
