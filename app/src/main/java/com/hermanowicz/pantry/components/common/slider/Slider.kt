package com.hermanowicz.pantry.components.common.slider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun SliderSettings(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    steps: Int = 0,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier.padding(LocalSpacing.current.medium)
    ) {
        Text(
            text = label,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        Slider(
            value = value,
            valueRange = valueRange,
            onValueChange = onValueChange,
            steps = steps,
            enabled = enabled
        )
        Text(
            text = value.toInt().toString(),
            style = TextStyle(fontSize = 14.sp)
        )
    }
}
