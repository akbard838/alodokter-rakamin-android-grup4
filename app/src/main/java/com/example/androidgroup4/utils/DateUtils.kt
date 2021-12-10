package com.example.androidgroup4.utils

import java.text.SimpleDateFormat
import java.util.*

fun getBirthDateFormat(dateResponse: String): String {
    val birthDate =
        if (dateResponse.contains('T')) dateResponse.substring(0, dateResponse.lastIndexOf('T'))
        else dateResponse

    if (dateResponse.contains('T')) {
        val input = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val output = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        val result = input.parse(birthDate)
        return output.format(result)
    }

    return birthDate
}