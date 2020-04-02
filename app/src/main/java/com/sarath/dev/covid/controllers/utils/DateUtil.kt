package com.sarath.dev.covid.controllers.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun current(): String {
            val date = Date(System.currentTimeMillis())
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T00:00:00Z'", Locale.getDefault())
            return dateFormatter.format(date)
        }
    }
}