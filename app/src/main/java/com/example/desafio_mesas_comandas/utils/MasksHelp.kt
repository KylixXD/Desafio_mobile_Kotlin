package com.example.desafio_mesas_comandas.utils

import java.text.NumberFormat
import java.util.Locale

fun Int.toBrazilianCurrencyFromCents(): String {
    val valueInReais = this / 100.0
    val locale = Locale.forLanguageTag("pt-BR")
    return NumberFormat.getCurrencyInstance(locale).format(valueInReais)
}

fun Int.maskForIdleTime(): String {
    if (this <= 0) {
        return "0 mins"
    }

    val minutesInHour = 60
    val minutesInDay = 24 * 60

    return when {
        this < minutesInHour -> "$this mins"

        this < minutesInDay -> {
            val hours = this / minutesInHour
            if (hours == 1) "$hours hora" else "$hours horas"
        }

        else -> {
            val days = this / minutesInDay
            if (days == 1) "$days dia" else "$days dias"
        }
    }
}