package com.hermanowicz.mypantry.components.common.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hermanowicz.mypantry.ui.theme.Blue500
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.ui.theme.Shapes

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

@Composable
fun ButtonPicker(text: String, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier.fillMaxHeight(),
        shape = Shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Blue500,
            contentColor = Blue500
        ),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            color = Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Column() {
        ButtonPrimary(text = "Test", onClick = { })
        ButtonPicker(text = "Test", onClick = { })
    }
}
