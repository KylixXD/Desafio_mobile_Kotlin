package com.example.desafio_mesas_comandas.data.repository

import android.app.Application
import com.example.desafio_mesas_comandas.data.local.OrderEntity
import com.example.desafio_mesas_comandas.utils.ReadJson
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val application: Application) {
    fun getOrdersForTableMock(mesaId: Int): List<OrderEntity> {
        return ReadJson.readOrdersMock(application, "Mock.json", mesaId)
    }
}