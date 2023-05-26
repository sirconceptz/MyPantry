package com.hermanowicz.pantry.components.common.picker

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.hermanowicz.pantry.components.common.textfield.TextFieldAndLabelDate
import com.hermanowicz.pantry.components.common.textfield.TextFieldDoubleAndLabelDate
import com.hermanowicz.pantry.utils.DateAndTimeConverter
import com.hermanowicz.pantry.utils.DatePickerData
import com.hermanowicz.pantry.utils.PickerType

@Composable
fun DatePickerPrimary(
    labelText: String,
    datePickerData: DatePickerData,
    dateToDisplay: String,
    onChangeDate: (DatePickerData) -> Unit,
    pickerType: PickerType
) {
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onChangeDate(DatePickerData(mDayOfMonth, mMonth, mYear))
        },
        datePickerData.year,
        datePickerData.month,
        datePickerData.day
    )
    val now = System.currentTimeMillis()
    if (pickerType == PickerType.FROM) {
        datePickerDialog.datePicker.maxDate = now
    }
    if (pickerType == PickerType.TO) {
        datePickerDialog.datePicker.minDate = now
    }

    TextFieldAndLabelDate(
        textfieldText = DateAndTimeConverter.dateToVisibleWithYear(dateToDisplay),
        labelText = labelText,
        onClickTextfield = { datePickerDialog.show() }
    )
}

@Composable
fun DatePickerDouble(
    labelText: String,
    datePickerLeftData: DatePickerData,
    datePickerRightData: DatePickerData,
    dateLeftToDisplay: String,
    dateRightToDisplay: String,
    onChangeLeftDate: (DatePickerData) -> Unit,
    onChangeRightDate: (DatePickerData) -> Unit
) {
    val datePickerLeftDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onChangeLeftDate(DatePickerData(mDayOfMonth, mMonth, mYear))
        },
        datePickerLeftData.year,
        datePickerLeftData.month,
        datePickerLeftData.day
    )
    val datePickerRightDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onChangeRightDate(DatePickerData(mDayOfMonth, mMonth, mYear))
        },
        datePickerRightData.year,
        datePickerRightData.month,
        datePickerRightData.day
    )

    TextFieldDoubleAndLabelDate(
        labelText = labelText,
        textfieldLeftText = DateAndTimeConverter.dateToVisibleWithYear(dateLeftToDisplay),
        textfieldRightText = DateAndTimeConverter.dateToVisibleWithYear(dateRightToDisplay),
        onClickLeftTextfield = { datePickerLeftDialog.show() },
        onClickRightTextfield = { datePickerRightDialog.show() }
    )
}
