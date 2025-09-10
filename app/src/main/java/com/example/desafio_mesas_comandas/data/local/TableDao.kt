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
        SELECT * FROM `CheckPad_Tables`
        WHERE (:searchText IS NULL 
               OR CAST(title AS TEXT) LIKE '%' || :searchText || '%' 
               OR customerName LIKE '%' || :searchText || '%' 
               OR sellerName LIKE '%' || :searchText || '%')
          AND (:activityType IS NULL OR activity = :activityType)
        ORDER BY title ASC
    """
    )
    fun getTablesPagingSource(
        searchText: String?,
        activityType: String?
    ): PagingSource<Int, TableEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<TableEntity>)

    @Query("SELECT * FROM `CheckPad_Tables` ORDER BY title ASC")
    fun getAll(): Flow<List<TableEntity>>

    @Query("SELECT COUNT(id) FROM `CheckPad_Tables`")
    suspend fun tableCount(): Int


}