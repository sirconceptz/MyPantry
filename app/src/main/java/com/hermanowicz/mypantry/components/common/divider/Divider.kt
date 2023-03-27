package com.hermanowicz.mypantry.components.common.divider

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Gray
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun DividerCardInside() {
    Divider(
        color = Gray,
        thickness = LocalSpacing.current.line
    )
}
