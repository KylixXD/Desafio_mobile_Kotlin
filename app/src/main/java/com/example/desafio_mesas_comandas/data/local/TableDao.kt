package com.example.desafio_mesas_comandas.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TableDao {
    @Query(
        """
        SELECT * FROM tables
        WHERE (:searchText IS NULL 
               OR CAST(title AS TEXT) LIKE '%' || :searchText || '%' 
               OR customerName LIKE '%' || :searchText || '%' 
               OR sellerName LIKE '%' || :searchText || '%')
          AND (:activityType IS NULL OR activity = :activityType)
        ORDER BY title ASC
    """
    )
    fun getFilteredTables(
        searchText: String?,
        activityType: String?
    ): Flow<List<TableEntity>>

    @Query(
        """
        SELECT * FROM tables
        WHERE (title LIKE '%' || :q || '%' OR customerName LIKE '%' || :q || '%')
          AND (activity = :statusFilter)
        ORDER BY title ASC
    """
    )
    fun paging(q: String, statusFilter: String): PagingSource<Int, TableEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<TableEntity>)

    @Query("SELECT * FROM tables ORDER BY title ASC")
    fun getAll(): Flow<List<TableEntity>>

    @Query("SELECT COUNT(id) FROM tables")
    suspend fun tableCount(): Int


}