package com.example.desafio_mesas_comandas.data.local

import androidx.room.PrimaryKey

data class OrderEntity(
    val id: Int,
    val tableId: Int,
    val description: String,
    val subtotal: Int,
    val customerName: String?,
    val hasPaid: Boolean
)
