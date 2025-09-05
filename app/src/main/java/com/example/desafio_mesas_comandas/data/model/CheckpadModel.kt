package com.example.desafio_mesas_comandas.data.model

// data class com referencia a todos os outros dataclasses
data class CheckpadApiResponse(
    val total: Int,
    val withOrderSheet: Int,
    val withoutOrderSheet: Int,
    val checkpads: List<Checkpad>?
)

data class OrderSheet(
    val id: Int,
    val info: String?,
    val user: User?,
    val opened: String,
    val seller: Seller?,
    val contact: String,
    val hasPaid: Boolean,
    val customer: Customer?,
    val idleTime: Int,
    val subtotal: Int,
    val hasOrders: Boolean,
    val customerName: String?,
    val lastOrderCreated: String? = null,
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
    val title: Int,
    val hasPdv: Boolean,
    val lastOrderCreated: String,
    val hasOrderSheets: Boolean,
    val hasOrder: Boolean,
    val idleTime: Int,
    val activity: String,
    val pdvDevices: List<PdvDevices> = emptyList(),
    val orderSheets: List<OrderSheet> = emptyList()
)

data class User(
    val id: Int,
    val name: String
)

data class Customer(
    val id: Int,
    val doc: String?,
    val name: String,
    val email: String?,
    val phone: String,
    val status: Int,
    val birthDate: String? = null,
    val additionalPhone: String? = null,
    val customerAccount: CustomerAccount? = null
)

data class CustomerAccount(
    val status: Int,
    val creditLimit: Int,
    val currentBalance: Int,
    val allowOnCustomerAccount: Int
)


