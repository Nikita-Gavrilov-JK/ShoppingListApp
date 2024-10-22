package com.example.shoppinglistapp.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeManager {
    fun getCurrentTime(): String {
        val format = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return format.format(Calendar.getInstance().time)
    }
}