package com.hermanowicz.mypantry.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

object DateAndTimeConverter {
    @SuppressLint("SimpleDateFormat")
    fun dateToVisibleWithYear(day: Int, month: Int, year: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val date = Date(calendar.timeInMillis)
        val format = SimpleDateFormat("dd MMM yyyy")
        return format.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun timeToVisible(hour: Int, minute: Int): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return LocalTime.of(hour, minute).format(formatter)
    }
}
