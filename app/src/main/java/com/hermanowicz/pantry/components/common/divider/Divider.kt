package com.hermanowicz.pantry.components.common.divider

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Gray
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun DividerCardInside() {
    Divider(
        color = Gray,
        thickness = LocalSpacing.current.line
    )
}
