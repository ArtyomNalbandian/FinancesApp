package com.example.financesapp.domain.repositories

import com.example.common.model.account.Account
import com.example.network.dto.account.AccountRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {

    suspend fun getAccount(): Account

    fun observeAccount(): Flow<Account>

    suspend fun updateAccount(accountId: Int, accountRequest: AccountRequestDto): Account

    val currentAccount: StateFlow<Account?>
    suspend fun refreshAccount()

    suspend fun getAccountById(accountId: String): Account
}
