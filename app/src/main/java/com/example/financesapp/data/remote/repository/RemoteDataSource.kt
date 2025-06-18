package com.example.financesapp.data.remote.repository

import com.example.financesapp.data.remote.models.account.AccountDto
import com.example.financesapp.data.remote.models.account.AccountHistoryResponse
import com.example.financesapp.data.remote.models.account.AccountRequest
import com.example.financesapp.data.remote.models.account.AccountResponse
import com.example.financesapp.data.remote.models.category.Category
import com.example.financesapp.data.remote.models.transaction.Transaction
import com.example.financesapp.data.remote.models.transaction.TransactionRequest
import com.example.financesapp.data.remote.models.transaction.TransactionResponseDto

//interface RemoteDataSource {
//
//    suspend fun getAccounts(): List<AccountDto>
//    suspend fun getAccountById(id: Int): AccountResponse
//    suspend fun createAccount(request: AccountRequest): AccountDto
//    suspend fun updateAccount(id: Int, request: AccountRequest): AccountDto
//    suspend fun deleteAccount(id: Int)
//    suspend fun getAccountHistory(id: Int): AccountHistoryResponse
//
//    suspend fun getCategories(): List<Category>
//    suspend fun getCategoriesByType(isIncome: Boolean): List<Category>
//
//    suspend fun createTransaction(request: TransactionRequest): Transaction
//    suspend fun getTransactionById(id: Int): TransactionResponseDto
//    suspend fun updateTransaction(id: Int, request: TransactionRequest): TransactionResponseDto
//    suspend fun deleteTransaction(id: Int)
//    suspend fun getTransactionsByPeriod(
//        accountId: Int,
//        startDate: String?,
//        endDate: String?
//    ): List<TransactionResponseDto>
//
//}