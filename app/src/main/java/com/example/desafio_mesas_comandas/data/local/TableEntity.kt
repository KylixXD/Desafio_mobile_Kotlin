package com.example.desafio_mesas_comandas.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.desafio_mesas_comandas.model.Customer

@Entity(tableName = "CheckPad Tables", indices = [Index("title", unique = true)])
data class TableEntity(
    @PrimaryKey val id: Int,
    val title: Int,
    val customerName: String?,
    val orderCount: Int,
    val idleTime: Int,
    val activity: String,
    val sellerName: String,
    val subTotal: Int?,
    val numberCustomer: Int?
)

