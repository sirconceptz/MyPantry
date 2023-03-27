package com.hermanowicz.mypantry.components.common.picker

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.hermanowicz.mypantry.components.common.button.ButtonPicker
import com.hermanowicz.mypantry.utils.DateAndTimeConverter
import com.hermanowicz.mypantry.utils.DatePickerData
import com.hermanowicz.mypantry.utils.PickerType

@Composable
fun DatePickerPrimary(
    datePickerData: DatePickerData,
    onChangeDate: (DatePickerData) -> Unit,
    pickerType: PickerType
) {
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onChangeDate(DatePickerData(mDayOfMonth, mMonth, mYear))
        }, datePickerData.year, datePickerData.month, datePickerData.day
    )
    val now = System.currentTimeMillis()
    if (pickerType == PickerType.FROM)
        datePickerDialog.datePicker.maxDate = now
    if (pickerType == PickerType.TO)
        datePickerDialog.datePicker.minDate = now

    ButtonPicker(
        text = DateAndTimeConverter.dateToVisibleWithYear(
            datePickerData.day,
            datePickerData.month,
            datePickerData.year
        ),
        onClick = { datePickerDialog.show() },
    )
}
