package com.example.project_movies_2021.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Double.toPtBrRealString(): String {
    val ptBrFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            as DecimalFormat
    ptBrFormat.minimumFractionDigits = 2
    val decimalFormatSymbols = ptBrFormat.decimalFormatSymbols
    decimalFormatSymbols.currencySymbol = ""

    ptBrFormat.decimalFormatSymbols = decimalFormatSymbols

    return ptBrFormat.format(this).trim()
}

fun String.toConvertRealDouble(): Double{
    return this
        .replace("R$", "")
        .replace(".", "")
        .replace(",", ".").toDouble()
}


