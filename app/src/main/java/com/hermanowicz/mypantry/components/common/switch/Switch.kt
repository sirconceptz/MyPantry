package com.hermanowicz.mypantry.components.common.switch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun SwitchPrimary(
    modifier: Modifier = Modifier,
    text: String = "Switch",
    state: Boolean = false,
    onStateChange: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(LocalSpacing.current.small))
            .background(color = Color.White)
            .padding(
                horizontal = LocalSpacing.current.medium,
                vertical = LocalSpacing.current.small
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text
        )
        Switch(
            checked = state,
            onCheckedChange = { onStateChange() }
        )
    }
}

@Composable
@Preview
fun previewSwitchPrimary() {
    Surface(color = Color(0xDCF5F5F5)) {
        Column(
            modifier = Modifier.padding(LocalSpacing.current.medium)
        ) {
            SwitchPrimary()
        }
    }
}
