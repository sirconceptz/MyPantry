package com.hermanowicz.pantry.components.common.picker

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.hermanowicz.pantry.components.common.button.ButtonPicker
import com.hermanowicz.pantry.utils.DateAndTimeConverter
import com.hermanowicz.pantry.utils.TimePickerData

@Composable
fun TimePickerPrimary(timePickerData: TimePickerData, onChange: (TimePickerData) -> Unit) {
    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, mHour: Int, mMinute: Int ->
            onChange(TimePickerData(mHour, mMinute))
        }, timePickerData.hour, timePickerData.minute, true
    )
    ButtonPicker(
        text = DateAndTimeConverter.timeToVisible(timePickerData.hour, timePickerData.minute),
        onClick = { timePickerDialog.show() },
    )
}
