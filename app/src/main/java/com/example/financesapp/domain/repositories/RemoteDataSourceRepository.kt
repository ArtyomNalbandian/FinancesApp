package com.example.financesapp.domain.repositories

import com.example.financesapp.data.remote.models.account.AccountDto
import com.example.financesapp.data.remote.models.account.AccountHistoryResponseDto
import com.example.financesapp.data.remote.models.account.AccountRequestDto
import com.example.financesapp.data.remote.models.account.AccountResponseDto
import com.example.financesapp.data.remote.models.category.Category
import com.example.financesapp.data.remote.models.transaction.Transaction
import com.example.financesapp.data.remote.models.transaction.TransactionRequest
import com.example.financesapp.data.remote.models.transaction.TransactionResponseDto

interface RemoteDataSourceRepository {

    suspend fun getAccount(): AccountDto
    suspend fun getAccountById(id: Int): AccountResponseDto
    suspend fun createAccount(request: AccountRequestDto): AccountDto
    suspend fun updateAccount(id: Int, request: AccountRequestDto): AccountDto
    suspend fun deleteAccount(id: Int)
    suspend fun getAccountHistory(id: Int): AccountHistoryResponseDto

    suspend fun getCategories(): List<Category>
    suspend fun getCategoriesByType(isIncome: Boolean): List<Category>

    suspend fun createTransaction(request: TransactionRequest): Transaction
    suspend fun getTransactionById(id: Int): TransactionResponseDto
    suspend fun updateTransaction(id: Int, request: TransactionRequest): TransactionResponseDto
    suspend fun deleteTransaction(id: Int)
    suspend fun getTransactionsByPeriod(startDate: String?, endDate: String?): List<TransactionResponseDto>

}