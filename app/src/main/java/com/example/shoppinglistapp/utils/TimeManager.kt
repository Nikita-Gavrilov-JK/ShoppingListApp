package com.example.shoppinglistapp.utils

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeManager {
    const val DEF_TIME_FORMAT = "hh:mm:ss - yyyy/MM/dd"
    fun getCurrentTime(): String {
        val format = SimpleDateFormat(DEF_TIME_FORMAT, Locale.getDefault())
        return format.format(Calendar.getInstance().time)
    }
    fun getTimeFormat(time: String, defPreferences: SharedPreferences): String {
        val defFormat = SimpleDateFormat(DEF_TIME_FORMAT, Locale.getDefault())
        val defDate = defFormat.parse(time)
        val newFormat = defPreferences.getString("format_time_key", DEF_TIME_FORMAT)
        val newDefFormat = SimpleDateFormat(newFormat, Locale.getDefault())
        return if (defDate != null){
            newDefFormat.format(defDate)
        } else {
            time
        }
    }
}