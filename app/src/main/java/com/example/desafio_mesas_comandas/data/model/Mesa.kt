package com.example.desafio_mesas_comandas.data.model

data class Mesa(val id: Int,
                val numeroMesa: Int,
                val status:String,
                val cliente: String?,
                val nomeCliente: String?,
                val nomeAtendente :String?)
