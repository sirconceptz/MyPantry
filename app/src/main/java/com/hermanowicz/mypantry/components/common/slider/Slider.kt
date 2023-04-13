package com.hermanowicz.mypantry.components.common.slider

import androidx.compose.material.Slider
import androidx.compose.runtime.Composable

@Composable
fun SliderSettings(
    value: Float,
    onValueChange: (Float) -> Unit,
    steps: Int = 0,
    enabled: Boolean = true
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        steps = steps,
        enabled = enabled
    )
}
