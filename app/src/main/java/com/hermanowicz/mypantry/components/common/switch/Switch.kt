package com.hermanowicz.mypantry.components.common.switch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.ui.theme.MyPantryTheme

@Composable
fun SwitchPrimary(
    modifier: Modifier = Modifier,
    label: String,
    state: Boolean,
    onStateChange: () -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(LocalSpacing.current.small))
            .background(color = Color.White)
            .padding(LocalSpacing.current.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = label,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (enabled) Color.Black else Color.Gray
                    )
                )
                Text(
                    text = if (state) stringResource(id = R.string.enabled) else stringResource(id = R.string.disabled),
                    style = TextStyle(fontSize = 14.sp),
                    color = if (enabled) Color.Black else Color.Gray
                )
            }
            Switch(checked = state, onCheckedChange = { onStateChange() }, enabled = enabled)
        }
    }
}

@Composable
@Preview
fun previewSwitchPrimary() {
    MyPantryTheme {
        Column(
            modifier = Modifier.padding(LocalSpacing.current.medium)
        ) {
            SwitchPrimary(label = "Switch", state = true, onStateChange = {})
        }
    }
}
