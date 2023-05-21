package com.hermanowicz.pantry.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

object DateAndTimeConverter {
    @SuppressLint("SimpleDateFormat")
    fun dateToVisibleWithYear(dateToConvert: String): String {
        val dateArray = dateToConvert.split("-")
        var convertedDate = ""
        if (dateArray.size == 3) {
            val calendar = Calendar.getInstance()
            calendar.set(dateArray[0].toInt(), dateArray[1].toInt() - 1, dateArray[2].toInt())
            val date = Date(calendar.timeInMillis)
            val format = SimpleDateFormat("dd MMM yyyy")
            convertedDate = format.format(date)
        }
        return convertedDate
    }

    @SuppressLint("SimpleDateFormat")
    fun timeToVisible(hour: Int, minute: Int): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return LocalTime.of(hour, minute).format(formatter)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateToDbFromDatePickerData(expirationDatePickerData: DatePickerData): String {
        val calendar = Calendar.getInstance()
        calendar.set(
            expirationDatePickerData.year,
            expirationDatePickerData.month,
            expirationDatePickerData.day
        )
        val date = Date(calendar.timeInMillis)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }
}
