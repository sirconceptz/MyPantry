package com.hermanowicz.mypantry.components.common.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun TextLabel(
    text: String
) {
    Text(
        text = text,
        style = TextStyle(fontSize = 20.sp)
    )
}

@Composable
fun TextSettingsButtonWithValue(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier.clickable {
            if (enabled)
                onClick()
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = label,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = value,
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun TextSettingsButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier.clickable {
            if (enabled)
                onClick()
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}
