package com.example.desafio_mesas_comandas.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders WHERE tableId = :mesaId")
    fun getOrdersByTable(mesaId: Int): Flow<List<OrderEntity>>
}