package com.example.desafio_mesas_comandas.data.repository

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.desafio_mesas_comandas.data.local.TableEntity
import kotlinx.coroutines.flow.Flow

class TableRepository(private val application: Application) {
    private val dao = AppDatabase.getDatabase(application).tableDao()

    fun getAllTables(): Flow<List<TableEntity>> = dao.getAll()

    suspend fun tableCount(): Int {
        return dao.tableCount()
    }

    fun getPaginatedTables(
        searchText: String?,
        activityType: String?
    ): Flow<PagingData<TableEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                dao.getTablesPagingSource(searchText, activityType)
            }
        ).flow
    }

    suspend fun upsertAll(tables: List<TableEntity>) {
        dao.upsertAll(tables)
    }
}
