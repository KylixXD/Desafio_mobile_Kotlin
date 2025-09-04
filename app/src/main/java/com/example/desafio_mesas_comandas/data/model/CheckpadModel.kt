package com.example.desafio_mesas_comandas.data.model

// data class com referencia a todos os outros dataclasses
data class CheckpadApiResponse(
    val total: Int,
    val withOrderSheet: Int,
    val withoutOrderSheet: Int,
    val checkpads: Checkpad
)

data class CheckpadTotal(
    val seller: Seller,
    val checkpad: Checkpad,
    val checkpadResponse: CheckpadApiResponse,
    val orderSheet: OrderSheet
)

data class OrderSheet(
    val id: Int,
    val info: String?,
    val user: String?,
    val opened: String,
    val seller: Seller?,
    val contact: String,
    val hasPaid: Boolean,
    val customer: String?,
    val idleTime: Int,
    val subtotal: Double,
    val hasOrders: Boolean,
    val customerName: String?,
    val lastOrderCreated: String,
    val numberOfCustomers: Int?
)

data class PdvDevices(
    val id: Int,
    val model: String,
    val serial: String
)

//Vendedor
data class Seller(val id: Int, val name: String)

//Mesa
data class Checkpad(
    val id: Int,
    val status: Boolean,
    val hash: String,
    val title: String,
    val hasPdv: Boolean,
    val lastOrderCreated: String,
    val hasOrderSheets: Boolean,
    val hasOrder: Boolean,
    val idleTime: Int,
    val activity: String,
    val pdvDevices: PdvDevices,
    val orderSheets: OrderSheet
)


