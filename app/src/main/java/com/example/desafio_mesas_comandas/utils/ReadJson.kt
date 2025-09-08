package com.example.desafio_mesas_comandas.utils

import android.content.Context
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
                TableEntity(
                    id = checkpad.id,
                    title = checkpad.title,
                    activity = checkpad.activity,
                    orderCount = checkpad.orderSheets.size,
                    customerName = checkpad.orderSheets.firstOrNull()?.customerName,
                    idleTime = checkpad.idleTime,
                    subTotal = checkpad.orderSheets.firstOrNull()?.subtotal,
                    sellerName = checkpad.orderSheets.firstOrNull()?.seller?.name.toString()
                )
            } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
