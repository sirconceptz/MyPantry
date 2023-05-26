package com.hermanowicz.pantry.components.common.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.ui.theme.Shapes

@Composable
fun CardWhiteBgWithBorder(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        border = BorderStroke(width = LocalSpacing.current.line, color = Color.Black),
        shape = Shapes.medium
    ) {
        Column {
            content()
        }
    }
}
