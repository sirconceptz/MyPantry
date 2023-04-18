package com.hermanowicz.pantry.components.common.checkbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleCheckbox(
    modifier: Modifier = Modifier.offset(x = 4.dp, y = 4.dp),
    selected: Boolean,
    enabled: Boolean = true,
    onChecked: () -> Unit
) {
    val imageVector = if (selected) Icons.Filled.CheckCircle else Icons.Outlined.Circle
    val tint = if (selected) Black.copy(alpha = 0.8f) else Black.copy(alpha = 0.8f)

    IconButton(
        onClick = { onChecked() },
        modifier = modifier,
        enabled = enabled
    ) {

        Icon(
            imageVector = imageVector, tint = tint,
            modifier = Modifier.background(White, shape = CircleShape),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun previewCircleCheckBox() {
    Surface(color = White) {
        Column() {
            CircleCheckbox(selected = true, onChecked = {})
            CircleCheckbox(selected = false, onChecked = {})
        }
    }
}
