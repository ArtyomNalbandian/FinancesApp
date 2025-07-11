package com.example.account.domain.repository

import com.example.common.model.account.Account
import com.example.network.dto.account.AccountRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {

    suspend fun getAccount(): Account
    suspend fun updateAccount(accountId: Int, accountRequest: AccountRequestDto): Account
}
