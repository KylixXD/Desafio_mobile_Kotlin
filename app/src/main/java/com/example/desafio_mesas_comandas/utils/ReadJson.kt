package com.example.desafio_mesas_comandas.utils

import android.content.Context
import com.example.desafio_mesas_comandas.data.model.CheckpadApiResponse
import com.google.gson.Gson
import java.io.InputStreamReader

object ReadJson{
    fun readJsonMock(context: Context, fileName: String): CheckpadApiResponse? {
        return try {
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)

            // O Gson converte automaticamente o JSON para a nossa classe principal
            Gson().fromJson(reader, CheckpadApiResponse::class.java)

        } catch (e: Exception) {
//            e.printStackTrace()
//            null
            throw RuntimeException("FALHA AO LER/CONVERTER O JSON! Causa:", e)
        }


    }
}
