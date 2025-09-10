package com.example.desafio_mesas_comandas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.FtsOptions
import com.example.desafio_mesas_comandas.MyApplication
import com.example.desafio_mesas_comandas.data.local.OrderEntity
import com.example.desafio_mesas_comandas.model.OrderSheet
import com.example.desafio_mesas_comandas.utils.ReadJson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(application: Application, private val mesaId: Int) : AndroidViewModel(application) {

    private val _orders = MutableStateFlow<List<OrderEntity>>(emptyList())
    val orders: StateFlow<List<OrderEntity>> = _orders

    fun loadOrders() {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val orderList = ReadJson.readOrdersMock(context, "Mock.json", mesaId)
            _orders.value = orderList
        }
    }
}

