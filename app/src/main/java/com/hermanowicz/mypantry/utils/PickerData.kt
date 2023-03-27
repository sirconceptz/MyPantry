package com.hermanowicz.mypantry.utils

import java.util.Calendar

data class TimePickerData(
    var hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    var minute: Int = Calendar.getInstance().get(Calendar.MINUTE)
)

data class DatePickerData(
    var day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    var month: Int = Calendar.getInstance().get(Calendar.MONTH),
    var year: Int = Calendar.getInstance().get(Calendar.YEAR)
)

enum class PickerType {
    FROM, TO
}
