package com.hermanowicz.pantry.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DateAndTimeConverterTest {
    private lateinit var dateToConvert: String

    @Before
    fun setUp() {
        dateToConvert = "2023-09-01"
    }

    @Test
    fun testDateToVisibleWithYear() {
        val convertedDate = DateAndTimeConverter.dateToVisibleWithYear(dateToConvert)
        assertEquals("01 Sep 2023", convertedDate)
    }

    @Test
    fun testTimeToVisible() {
        val visibleTime = DateAndTimeConverter.timeToVisible(10, 30)
        assertEquals("10:30", visibleTime)
    }

    @Test
    fun testGetDateToDbFromDatePickerData() {
        val datePickerData = DatePickerData(1, 8, 2023) // Assuming August is represented as 8
        val dateToDb = DateAndTimeConverter.getDateToDbFromDatePickerData(datePickerData)
        assertEquals("2023-09-01", dateToDb)
    }
}
