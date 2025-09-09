package com.example.desafio_mesas_comandas.utils

import java.text.NumberFormat
import java.util.Locale

fun Int.toBrazilianCurrencyFromCents(): String {
    val valueInReais = this / 100.0
    val locale = Locale.forLanguageTag("pt-BR")
    return NumberFormat.getCurrencyInstance(locale).format(valueInReais)
}