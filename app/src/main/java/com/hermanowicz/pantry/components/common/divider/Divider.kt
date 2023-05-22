package com.hermanowicz.pantry.components.common.divider

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Gray
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun DividerCardInside() {
    Divider(
        color = MaterialTheme.colors.onSurface,
        thickness = LocalSpacing.current.line
    )
}
