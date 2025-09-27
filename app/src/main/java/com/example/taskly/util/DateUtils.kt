package com.example.taskly.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun format(timestamp: Long, withTime: Boolean = false): String {
        val pattern = if (withTime) "dd/MM/yyyy HH:mm" else "dd/MM/yyyy"
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(timestamp))
    }
}
