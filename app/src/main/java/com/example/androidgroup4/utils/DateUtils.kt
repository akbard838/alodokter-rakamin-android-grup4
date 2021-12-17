package com.example.androidgroup4.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.changeDateFormat(currentFormat: String, newFormat: String): String {
    val locale = Locale("id", "ID")
    val formatIn = SimpleDateFormat(currentFormat, locale)
    val formatOut = SimpleDateFormat(newFormat, locale)
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = formatIn.parse(this)!!

    return formatOut.format(calendar.time)
}