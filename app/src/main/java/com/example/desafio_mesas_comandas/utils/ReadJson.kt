package com.example.desafio_mesas_comandas.utils

import android.content.Context
import com.example.desafio_mesas_comandas.data.local.OrderEntity
import com.example.desafio_mesas_comandas.data.local.TableEntity
import com.example.desafio_mesas_comandas.model.CheckpadApiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

object ReadJson {

    fun readJsonMock(context: Context, fileName: String): List<TableEntity> {
        return try {
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)

            val type = object : TypeToken<CheckpadApiResponse>() {}.type
            val response: CheckpadApiResponse = Gson().fromJson(reader, type)



            response.checkpads?.map { checkpad ->
                val rawSellerName = checkpad.orderSheets.firstOrNull()?.seller?.name
                val sellerName = if (rawSellerName.isNullOrBlank() || rawSellerName.equals(
                        "null",
                        ignoreCase = true
                    )
                ) {
                    ""
                } else {
                    rawSellerName
                }
                TableEntity(
                    id = checkpad.id,
                    title = checkpad.title,
                    activity = checkpad.activity,
                    orderCount = checkpad.orderSheets.size,
                    customerName = checkpad.orderSheets.firstOrNull()?.customerName,
                    idleTime = checkpad.idleTime,
                    subTotal = checkpad.orderSheets.firstOrNull()?.subtotal,
                    sellerName = sellerName,
                    numberCustomer = checkpad.orderSheets.firstOrNull()?.numberOfCustomers
                )
            } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    fun readOrdersMock(context: Context, fileName: String, mesaId: Int): List<OrderEntity> {
        return try {
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<CheckpadApiResponse>() {}.type
            val response: CheckpadApiResponse = Gson().fromJson(reader, type)

            response.checkpads
                ?.firstOrNull { it.id == mesaId }
                ?.orderSheets
                ?.map { order ->
                    OrderEntity(
                        id = order.id,
                        tableId = mesaId,
                        description = order.info ?: "Pedido sem descrição",
                        subtotal = order.subtotal,
                        customerName = order.customerName,
                        hasPaid = order.hasPaid
                    )
                } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    }
}
