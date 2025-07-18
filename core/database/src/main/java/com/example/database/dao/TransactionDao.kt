package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.database.entity.TransactionEntity
import com.example.database.entity.TransactionWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    fun getAll(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<TransactionEntity>)

    @Query("SELECT * FROM transactions WHERE isDirty = 1")
    suspend fun getDirty(): List<TransactionEntity>

    @Update
    suspend fun update(category: TransactionEntity)

    @Transaction
    @Query("SELECT * FROM transactions")
    fun getAllWithCategory(): Flow<List<TransactionWithCategory>>

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Int)
}
