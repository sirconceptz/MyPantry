package com.hermanowicz.mypantry.components.common.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun ButtonPrimary(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(vertical = LocalSpacing.current.tiny),
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun Preview() {
    Column() {
        ButtonPrimary(text = "Test", { })
    }
}
