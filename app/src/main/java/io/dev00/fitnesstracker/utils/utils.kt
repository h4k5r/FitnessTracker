package io.dev00.fitnesstracker.utils

import java.util.*

fun fetchCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    return "${day}/${month + 1}/${year}"
}