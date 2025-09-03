package com.example.desafio_mesas_comandas.data.model

//Puxar as Mesas totais
data class CheckpadResponse(
    val total: Int,
    val withOrderSheet: Int,
    val withoutOrderSheet: Int,
    val mesa: Checkpad
)
