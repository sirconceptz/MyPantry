package com.hermanowicz.mypantry.components.common.divider

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun DividerCardInside() {
    Divider(
        modifier = Modifier.padding(horizontal = LocalSpacing.current.small),
        color = Gray,
        thickness = LocalSpacing.current.line
    )
}
